package com.pandama.top.file.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description: 文件中心启动类
 * @author: 王强
 * @dateTime: 2023-06-17 14:49:00
 */
@EnableDiscoveryClient
@SpringBootApplication
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }

}
