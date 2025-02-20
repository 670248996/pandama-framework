package com.pandama.top.milvus.factory;

import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * MilvusRestClient工厂
 *
 * @author 王强
 * @date 2024-04-09 09:50:30
 */
@Slf4j
public class MilvusRestClientFactory {

    private static String IP_ADDR;
    private static Integer PORT;
    private static String DATABASE;
    private static String USERNAME;
    private static String PASSWORD;

    private MilvusServiceClient milvusServiceClient;

    private static final MilvusRestClientFactory milvusRestClientFactory = new MilvusRestClientFactory();

    private MilvusRestClientFactory() {

    }

    public static MilvusRestClientFactory build(String ipAddr, Integer port, String database, String username, String password) {
        IP_ADDR = ipAddr;
        PORT = port;
        DATABASE = database;
        USERNAME = username;
        PASSWORD = password;
        return milvusRestClientFactory;
    }

    public void init() {
        ConnectParam.Builder builder = ConnectParam.newBuilder()
                .withHost(IP_ADDR)
                .withPort(PORT)
                .withDatabaseName(DATABASE)
                .withConnectTimeout(2, TimeUnit.SECONDS);
        if (!StringUtils.isAnyBlank(USERNAME, PASSWORD)) {
            builder.withAuthorization(USERNAME, PASSWORD);
        }
        milvusServiceClient = new MilvusServiceClient(builder.build());
    }

    public MilvusServiceClient getMilvusClient() {
        return milvusServiceClient;
    }

    public void close() {
        if (milvusServiceClient != null) {
            try {
                milvusServiceClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
