package com.pandama.top.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 网关白名单配置
 *
 * @author 王强
 * @date 2023-07-08 15:38:06
 */
@Data
@ConfigurationProperties(prefix="spring.security.ignore")
public class IgnoreUrlsConfig {
    private List<String> urls;
}
