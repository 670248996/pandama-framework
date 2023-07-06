package com.pandama.top.auth.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description: 身份验证应用程序
 * @author: 王强
 * @dateTime: 2023-06-21 21:34:21
 */
@EnableFeignClients(basePackages = "com.pandama.top.user.api")
@EnableDiscoveryClient
@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class);
    }
}
