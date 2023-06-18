package com.pandama.top.file.biz.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 自定义线程池配置
 * @author: 白剑民
 * @dateTime: 2022/9/20 21:05
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "file.upload")
public class FileUploadProperties {
    /**
     * 文件上传根路径
     */
    private String rootDir = "upload";
    /**
     * 最大线程数
     */
    private Integer maxThreadSize = 8;
    /**
     * 队列大小
     */
    private Integer maxQueueSize = 1024;
}
