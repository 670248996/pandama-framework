package com.pandama.top.activiti;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 工作流应用程序
 *
 * @author 王强
 * @date 2023-07-08 15:58:15
 */
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.pandama.top.activiti.mapper")
@SpringBootApplication
public class ActivitiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class);
    }
}
