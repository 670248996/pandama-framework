package com.pandama.top.minio.conf;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * minio配置
 *
 * @author 王强
 * @date 2023-07-08 15:25:17
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {
    /**
     * 访问地址
     */
    private String endpoint;

    /**
     * accessKey类似于用户ID，用于唯一标识你的账户
     */
    private String accessKey;

    /**
     * secretKey是你账户的密码
     */
    private String secretKey;

    /**
     * 默认存储桶
     */
    private String bucketName;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
