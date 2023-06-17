package com.pandama.top.rocketmq.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rocketmq.enhance")
public class RocketEnhanceProperties {
    private boolean enabledIsolation = true;
    private String environment = "";
}
