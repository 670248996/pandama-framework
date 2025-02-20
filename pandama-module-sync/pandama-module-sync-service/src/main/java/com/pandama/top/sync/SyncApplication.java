package com.pandama.top.sync;

import com.pandama.top.sync.pojo.vo.JobParam;
import com.pandama.top.sync.service.ThirdService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.time.LocalDate;

/**
 * 日志应用程序
 *
 * @author 王强
 * @date 2023-07-08 15:58:15
 */
@Slf4j
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.pandama.top.sync.mapper")
@SpringBootApplication
public class SyncApplication implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(SyncApplication.class, args);
    }

    @Autowired
    private ThirdService thirdService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParam jobParam = new JobParam();
        jobParam.setStartDate(LocalDate.now().plusDays(-15));
        jobParam.setEndDate(LocalDate.now().plusDays(-10));
        while (jobParam.getEndDate().isAfter(jobParam.getStartDate())) {
            log.info("同步亚信的设备数据: " + jobParam.getStartDate());
            LocalDate endDate = jobParam.getStartDate().plusDays(1L);
            thirdService.syncYXDeviceDataToDB(jobParam.getStartDate(), endDate);
            jobParam.setStartDate(endDate);
        }
    }
}
