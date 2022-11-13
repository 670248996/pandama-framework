package com.pandama.top.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: redis自定义配置类
 * @author: 白剑民
 * @dateTime: 2022/8/18 15:44
 */
@Data
@ConfigurationProperties(prefix = "spring.redis.custom")
public class RedisCustomProperties {
    /**
     * 订阅频道1
     */
    private String channel1;
}
