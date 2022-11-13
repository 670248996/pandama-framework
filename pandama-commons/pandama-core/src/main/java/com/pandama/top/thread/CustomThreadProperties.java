package com.pandama.top.thread;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: 自定义线程池配置
 * @author: 白剑民
 * @dateTime: 2022/9/20 21:05
 */
@Data
@ConfigurationProperties(prefix = "custom.thread")
public class CustomThreadProperties {
    /**
     * 核心线程数
     */
    private Integer corePoolSize = 2;
    /**
     * 最大线程数
     */
    private Integer maxPoolSize = 8;
    /**
     * 队列大小
     */
    private Integer queueCapacity = 100;
    /**
     * 线程名前缀
     */
    private String threadName = "async-service-";
}
