package com.pandama.top.app;


import com.alibaba.nacos.api.exception.NacosException;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 配置文件加密测试
 *
 * @author 王强
 * @date 2023-08-15 13:53:50
 */
@SpringBootTest
class ConfigFileEncryptTest {

    /**
     * 加密pwd
     */
    private static final String encryptPwd = "pandama_encrypt_key";

    /**
     * 需要加密的文件路径
     */
    private static final String encryptFilePath = "src/main/resources/decrypt.yml";

    /**
     * 加密后生成文件路径
     */
    private static final String generateFilePath = "src/main/resources/encrypt.yml";

    @Test
    @SuppressWarnings("all")
    public void encrypt() throws IOException, NacosException {
        // 初始化加密工具
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(encryptPwd);

        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        // YAML类是API的入口点
        Yaml yaml = new Yaml(dumperOptions);
        // 使用load方法把指定yml文件读取为LinkedHashMap
        Object load = yaml.load(Files.newInputStream(Paths.get(encryptFilePath)));
        HashMap<String, Object> ymlMap = load instanceof HashMap ? (HashMap<String, Object>) load : new LinkedHashMap<>();
        // 定义yml文件
        FileWriter fileWriter = new FileWriter(generateFilePath);
        // 对读取的yml文件数据进行加密
        encryptMap(ymlMap, encryptor);
        // dump方法生成yaml
        yaml.dump(ymlMap, fileWriter);
    }

    @SuppressWarnings("all")
    private void encryptMap(HashMap<String, Object> ymlMap, StandardPBEStringEncryptor encryptor) {
        for (Map.Entry<String, Object> entry : ymlMap.entrySet()) {
            if (entry.getValue() instanceof String) {
                entry.setValue("ENC(" + encryptor.encrypt(entry.getValue().toString()) + ")");
            }
            if (entry.getValue() instanceof HashMap) {
                encryptMap((HashMap<String, Object>) entry.getValue(), encryptor);
            }
        }

    }
}