package com.pandama.top.milvus.factory;

import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * MilvusRestClient工厂
 *
 * @author 王强
 * @date 2024-04-09 09:50:30
 */
public class MilvusRestClientFactory {

    private static String IP_ADDR;

    private static Integer PORT;
    private static String DATABASE;

    private MilvusServiceClient milvusServiceClient;

    private static final MilvusRestClientFactory milvusRestClientFactory = new MilvusRestClientFactory();

    private MilvusRestClientFactory() {

    }

    public static MilvusRestClientFactory build(String ipAddr, Integer port, String database) {
        IP_ADDR = ipAddr;
        PORT = port;
        DATABASE = database;
        return milvusRestClientFactory;
    }

    public void init() {
        ConnectParam connectParam = ConnectParam.newBuilder()
                .withHost(IP_ADDR)
                .withPort(PORT)
                .withDatabaseName(DATABASE)
                .withConnectTimeout(2, TimeUnit.SECONDS)
                .build();
        milvusServiceClient = new MilvusServiceClient(connectParam);
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
