package com.pandama.top.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description: 应用程序应用
 * @author: 王强
 * @dateTime: 2023-04-19 11:22:24
 */
@EnableDiscoveryClient
@MapperScan("com.pandama.top.app.mapper")
@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
