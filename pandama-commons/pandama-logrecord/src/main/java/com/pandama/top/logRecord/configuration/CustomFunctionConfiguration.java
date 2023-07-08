package com.pandama.top.logRecord.configuration;

import com.pandama.top.logRecord.function.CustomFunctionRegistrar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义函数配置
 *
 * @author 王强
 * @date 2023-07-08 15:21:27
 */
@Slf4j
@Configuration
public class CustomFunctionConfiguration {

    /**
     * 注册自定义方法
     *
     * @return com.pandama.top.logRecord.function.CustomFunctionRegistrar
     * @author 王强
     */
    @Bean
    public CustomFunctionRegistrar registrar() {
        return new CustomFunctionRegistrar();
    }
}
