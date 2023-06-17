package com.pandama.top.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description: 用户中心启动类
 * @author: 白剑民
 * @dateTime: 2022/10/17 15:46
 */
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.pandama.top.user.mapper")
@SpringBootApplication
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
