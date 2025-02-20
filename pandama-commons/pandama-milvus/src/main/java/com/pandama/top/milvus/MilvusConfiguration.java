package com.pandama.top.milvus;

import com.pandama.top.milvus.factory.MilvusRestClientFactory;
import com.pandama.top.milvus.utils.MilvusUtils;
import io.milvus.client.MilvusServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Milvus自动配置类
 *
 * @author 王强
 * @date 2024-04-09 09:49:33
 */
@Configuration
public class MilvusConfiguration {

    @Value("${milvus.ipAddr}")
    private String ipAddr;
    @Value("${milvus.port}")
    private Integer port;
    @Value("${milvus.database:default}")
    private String database;
    @Value("${milvus.username:}")
    private String username;
    @Value("${milvus.password:}")
    private String password;

    @Bean
    public MilvusServiceClient getMilvusClient() {
        return getMilvusFactory().getMilvusClient();
    }

    @Bean
    public MilvusUtils getMilvusUtils(MilvusServiceClient milvusClient) {
        return new MilvusUtils(milvusClient);
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public MilvusRestClientFactory getMilvusFactory() {
        return MilvusRestClientFactory.build(ipAddr, port, database, username, password);
    }
}
