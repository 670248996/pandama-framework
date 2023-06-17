package com.pandama.top.rocketmq.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: 火箭提升属性
 * @author: 王强
 * @dateTime: 2023-06-17 13:54:13
 */
@Data
@ConfigurationProperties(prefix = "rocketmq.enhance")
public class RocketEnhanceProperties {
    private boolean enabledIsolation = true;
    private String environment = "";
}
