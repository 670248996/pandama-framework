package com.pandama.top.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用户应用程序
 *
 * @author 王强
 * @date 2023-07-08 15:58:15
 */
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.pandama.top.user.mapper")
@SpringBootApplication
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(UserApplication.class);
        // 通过 SpringApplication 注册 ApplicationContextInitializer
        application.addInitializers(new MyApplicationContextInitializer());
        application.run(args);
    }

}
