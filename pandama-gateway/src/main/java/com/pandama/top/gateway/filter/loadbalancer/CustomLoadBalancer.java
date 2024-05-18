package com.pandama.top.gateway.filter.loadbalancer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义负载均衡器
 *
 * @author 王强
 * @date 2024-05-17 17:47:27
 */
@Slf4j
public class CustomLoadBalancer implements ReactorServiceInstanceLoadBalancer {
    private final String serviceId;
    private final AtomicInteger position;
    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    public CustomLoadBalancer(String serviceId, ObjectProvider<ServiceInstanceListSupplier> provider) {
        this(serviceId, new Random().nextInt(1000), provider);
    }

    public CustomLoadBalancer(String serviceId, int seed, ObjectProvider<ServiceInstanceListSupplier> provider) {
        this.serviceId = serviceId;
        this.position = new AtomicInteger(seed);
        this.serviceInstanceListSupplierProvider = provider;
    }

    /**
     * 选择
     *
     * @param request 请求
     * @return reactor.core.publisher.Mono<org.springframework.cloud.client.loadbalancer.Response < org.springframework.cloud.client.ServiceInstance>>
     * @author 王强
     */
    public Mono<Response<ServiceInstance>> choose(Request request) {
        log.info("===================自定义负载均衡策略===================");
        // 获得自定义请求的上下文，原本的request包含信息太少，且无请求体
        ServiceInstanceListSupplier supplier = this.serviceInstanceListSupplierProvider
                .getIfAvailable(NoopServiceInstanceListSupplier::new);
        return this.process(request, supplier);
    }

    /**
     * 处理返回Mono响应
     *
     * @author 王强
     */
    private Mono<Response<ServiceInstance>> process(Request request, ServiceInstanceListSupplier supplier) {
        return supplier.get(request).next().map((serviceInstances) ->
                this.processInstanceResponse(supplier, getInstanceResponse(serviceInstances)));
    }

    /**
     * 处理服务实例响应
     *
     * @author 王强
     */
    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier, Response<ServiceInstance> serviceInstanceResponse) {
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback)supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }

        return serviceInstanceResponse;
    }

    /**
     * 获得服务实例响应
     *
     * @author 王强
     */
    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {
        // 若服务实例列表为空
        if (instances.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("No servers available for service:" + this.serviceId);
            }
            return new EmptyResponse();
        } else {
            // 是否开启有点本地服务调用策略
            if (true) {
                return getLocalInstance(instances);
            }
            return getRoundRobinInstance(instances);
        }
    }

    /**
     * 本地获取服务实例
     *
     * @author 王强
     */
    private Response<ServiceInstance> getLocalInstance(List<ServiceInstance> instances) {
        log.info("===================本地获取服务实例策略===================");
        int idx = 0;
        String myNodeIp = System.getenv("MY_NODE_IP");
        if (StringUtils.isNotBlank(myNodeIp)) {
            for (ServiceInstance instance : instances) {
                String instanceNodeIp = instance.getMetadata().get("node-ip");
                if (StringUtils.equals(instanceNodeIp, myNodeIp)) {
                    log.info("choose local instance: " + instance.getHost() + instance.getPort());
                    return new DefaultResponse(instances.get(idx));
                }
                idx++;
            }
        }
        log.info("no matched instance from local");
        return getRoundRobinInstance(instances);
    }

    /**
     * 轮询获取服务实例
     *
     * @author 王强
     */
    private Response<ServiceInstance> getRoundRobinInstance(List<ServiceInstance> instances) {
        log.info("===================轮询获取服务实例策略===================");
        int pos = this.position.incrementAndGet() & Integer.MAX_VALUE;
        ServiceInstance instance = instances.get(pos % instances.size());
        return new DefaultResponse(instance);
    }

}
