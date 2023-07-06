package com.pandama.top.app;

import com.pandama.top.app.service.DeviceService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

/**
 * @description: 应用程序应用
 * @author: 王强
 * @dateTime: 2023-04-19 11:22:24
 */
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.pandama.top.app.mapper")
@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) throws Exception {


        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy", new Class[]{DeviceService.class});
        FileOutputStream fileOutputStream = new FileOutputStream("Proxy.class");
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();

//        ConfigurableApplicationContext run = SpringApplication.run(AppApplication.class, args);
//        DeviceService deviceService = (DeviceService) run.getBeanFactory().getBean("deviceServiceImpl");
//        System.out.println(deviceService.detail(1L));
    }
}
