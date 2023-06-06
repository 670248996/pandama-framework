package com.pandama.top.global;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.pandama.top.global.exception.CommonExtHandler;
import com.pandama.top.interceptor.CustomHandlerInterceptor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: 全局服务自动配置类
 * @author: 白剑民
 * @dateTime: 2022/10/12 15:59
 */
@Configuration
@Import({CommonExtHandler.class})
public class GlobalAutoConfiguration implements WebMvcConfigurer {

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

    /**
     * @param registry 注册表
     * @description: 添加拦截器
     * @author: 王强
     * @date: 2023-02-14 17:26:00
     * @return: void
     * @version: 1.0
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/","/css/**","fonts/**","/images/**","/js/**");
    }

    /**
     * @description: spring security密码加密策略
     * @author: 白剑民
     * @date: 2023-05-23 09:41:33
     * @return: org.springframework.security.crypto.password.PasswordEncoder
     * @version: 1.0
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
