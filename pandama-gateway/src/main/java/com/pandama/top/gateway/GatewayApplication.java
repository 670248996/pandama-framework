package com.pandama.top.gateway;

import com.pandama.top.gateway.filter.loadbalancer.CustomLoadBalanceClientConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;

/**
 * 网关应用程序
 *
 * @author 王强
 * @date 2023-07-08 15:39:49
 */
@EnableDiscoveryClient
@SpringBootApplication
@LoadBalancerClients(defaultConfiguration = CustomLoadBalanceClientConfiguration.class)
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
