package com.pandama.top.gateway.filter.loadbalancer;

import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 自定义负载平衡客户端配置
 *
 * @author 王强
 * @date 2024-05-18 10:01:13
 */
@Configuration
public class CustomLoadBalanceClientConfiguration {

    @Bean
    public ReactorServiceInstanceLoadBalancer reactorServiceInstanceLoadBalancer(Environment environment, LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new CustomLoadBalancer(name, loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class));
    }

    @Bean
    public ReactiveLoadBalancerClientFilter reactiveLoadBalancerClientFilter(LoadBalancerClientFactory clientFactory,
                                                                             GatewayLoadBalancerProperties properties) {
        return new CustomReactiveLoadBalancerClientFilter(clientFactory, properties);
    }

}

