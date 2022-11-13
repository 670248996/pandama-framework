package com.pandama.top.logRecord.configuration;

import com.pandama.top.logRecord.function.CustomFunctionRegistrar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 自定义函数配置
 * @author: 王强
 * @dateTime: 2022-09-02 17:23:03
 */
@Slf4j
@Configuration
public class CustomFunctionConfiguration {

    /**
     * @description: 注册自定义方法
     * @author: 王强
     * @date: 2022-09-03 22:39:06
     * @return: @return {@code CustomFunctionRegistrar }
     * @version: 1.0
     */
    @Bean
    public CustomFunctionRegistrar registrar() {
        return new CustomFunctionRegistrar();
    }
}
