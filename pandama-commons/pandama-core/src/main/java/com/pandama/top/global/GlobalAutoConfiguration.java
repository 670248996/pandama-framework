package com.pandama.top.global;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 全局服务自动配置类
 * @author: 白剑民
 * @dateTime: 2022/10/12 15:59
 */
@Configuration
public class GlobalAutoConfiguration {

    /**
     * @description: 使用Jackson将控制层返回的Long型数据转换成String类型，避免精度丢失
     * @author: 白剑民
     * @date: 2022-10-12 16:10:25
     * @return: org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
     * @version: 1.0
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return j -> j.serializerByType(Long.class, new ToStringSerializer());
    }

}
