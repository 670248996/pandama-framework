package com.pandama.top.gateway.filter.loadbalancer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.gateway.support.DelegatingServiceInstance;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Set;

/**
 * 自定义反应式负载均衡器客户端筛选器
 *
 * @author 王强
 * @date 2024-05-17 17:49:59
 */
@Slf4j
public class CustomReactiveLoadBalancerClientFilter extends ReactiveLoadBalancerClientFilter {
    public static final int LOAD_BALANCER_CLIENT_FILTER_ORDER = 10150;
    private final LoadBalancerClientFactory clientFactory;
    private final GatewayLoadBalancerProperties properties;

    public CustomReactiveLoadBalancerClientFilter(LoadBalancerClientFactory clientFactory, GatewayLoadBalancerProperties properties) {
        super(clientFactory, properties);
        this.clientFactory = clientFactory;
        this.properties = properties;
    }

    public int getOrder() {
        return LOAD_BALANCER_CLIENT_FILTER_ORDER;
    }

    /**
     * 过滤器
     *
     * @param exchange 交换
     * @param chain    链
     * @return reactor.core.publisher.Mono<java.lang.Void>
     * @author 王强
     */
    @SuppressWarnings("all")
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI url = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        // 获取原始路由前缀 若包含 lb 则表示需要进行负载均衡
        String schemePrefix = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR);
        if (url != null && ("lb".equals(url.getScheme()) || "lb".equals(schemePrefix))) {
            ServerWebExchangeUtils.addOriginalRequestUrl(exchange, url);
            if (log.isTraceEnabled()) {
                log.trace(ReactiveLoadBalancerClientFilter.class.getSimpleName() + " url before: " + url);
            }
            URI requestUri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
            String serviceId = requestUri.getHost();
            Set<LoadBalancerLifecycle> supportedLifecycleProcessors = LoadBalancerLifecycleValidator.getSupportedLifecycleProcessors(this.clientFactory.getInstances(serviceId, LoadBalancerLifecycle.class), RequestDataContext.class, ResponseData.class, ServiceInstance.class);
            // 自定义请求，将参数ServerWebExchange作为context传递，使得自定义负载均衡器中可以使用
            DefaultRequest<ServerWebExchange> lbRequest = new CustomRequest(exchange);
            return this.choose(lbRequest, serviceId, supportedLifecycleProcessors).flatMap(response -> {
                if (!response.hasServer()) {
                    supportedLifecycleProcessors.forEach((lifecycle) -> lifecycle.onComplete(new CompletionContext(CompletionContext.Status.DISCARD, lbRequest, response)));
                    throw NotFoundException.create(this.properties.isUse404(), "Unable to find instance for " + url.getHost());
                } else {
                    ServiceInstance retrievedInstance = response.getServer();
                    URI uri = exchange.getRequest().getURI();
                    String overrideScheme = retrievedInstance.isSecure() ? "https" : "http";
                    if (schemePrefix != null) {
                        overrideScheme = url.getScheme();
                    }

                    DelegatingServiceInstance serviceInstance = new DelegatingServiceInstance(retrievedInstance, overrideScheme);
                    URI requestUrl = this.reconstructURI(serviceInstance, uri);
                    if (log.isTraceEnabled()) {
                        log.trace("LoadBalancerClientFilter url chosen: " + requestUrl);
                    }
                    exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, requestUrl);
                    exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_LOADBALANCER_RESPONSE_ATTR, response);
                    supportedLifecycleProcessors.forEach((lifecycle) -> lifecycle.onStartRequest(lbRequest, response));
                }
                return chain.filter(exchange);
            });
        } else {
            return chain.filter(exchange);
        }
    }

    /**
     * 重建 URI
     *
     * @param serviceInstance 服务实例
     * @param original        源语言
     * @return java.net.URI
     * @author 王强
     */
    protected URI reconstructURI(ServiceInstance serviceInstance, URI original) {
        return LoadBalancerUriTools.reconstructURI(serviceInstance, original);
    }

    /**
     * 获取对应负载均衡器并调用choose方法
     *
     * @param lbRequest
     * @param serviceId
     * @param supportedLifecycleProcessors
     * @author 王强
     */
    private Mono<Response<ServiceInstance>> choose(Request<ServerWebExchange> lbRequest, String serviceId, Set<LoadBalancerLifecycle> supportedLifecycleProcessors) {
        ReactorLoadBalancer<ServiceInstance> loadBalancer = this.clientFactory.getInstance(serviceId, ReactorServiceInstanceLoadBalancer.class);
        if (loadBalancer == null) {
            throw new NotFoundException("No loadbalancer available for " + serviceId);
        } else {
            supportedLifecycleProcessors.forEach((lifecycle) -> lifecycle.onStart(lbRequest));
            return loadBalancer.choose(lbRequest);
        }
    }
}

