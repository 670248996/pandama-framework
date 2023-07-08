package com.pandama.top.rocketmq.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 火箭提升属性
 *
 * @author 王强
 * @date 2023-07-08 15:29:25
 */
@Data
@ConfigurationProperties(prefix = "rocketmq.enhance")
public class RocketEnhanceProperties {
    private boolean enabledIsolation = true;
    private String environment = "";
}
