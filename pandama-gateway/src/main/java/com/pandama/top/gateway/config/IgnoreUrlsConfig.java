package com.pandama.top.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @description: 网关白名单配置
 * @author: 王强
 * @dateTime: 2023-06-22 23:38:29
 */
@Data
@ConfigurationProperties(prefix="spring.security.ignore")
public class IgnoreUrlsConfig {
    private List<String> urls;
}
