package com.pandama.top.thread.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义线程池配置
 *
 * @author 王强
 * @date 2023-07-08 15:35:58
 */
@Data
@ConfigurationProperties(prefix = "pandama.thread")
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
