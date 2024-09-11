package com.pandama.top.camunda;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
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
@MapperScan("com.pandama.top.camunda.mapper")
@SpringBootApplication
@EnableProcessApplication
public class CamundaApplication {
    public static void main(String[] args) {
        SpringApplication.run(CamundaApplication.class);
    }
}
