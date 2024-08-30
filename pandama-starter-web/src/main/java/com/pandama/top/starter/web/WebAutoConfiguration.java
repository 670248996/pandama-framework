package com.pandama.top.starter.web;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.pandama.top.starter.web.authCode.AuthCodeConfiguration;
import com.pandama.top.starter.web.intercepter.CommonExtHandler;
import com.pandama.top.starter.web.intercepter.CustomHandlerInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局服务自动配置类
 *
 * @author 王强
 * @date 2023-07-08 15:35:03
 */
@Configuration
@Import({CommonExtHandler.class, AuthCodeConfiguration.class})
@ComponentScan(basePackages = {"com.pandama.top.starter.web"})
public class WebAutoConfiguration implements WebMvcConfigurer {

    /**
     * 使用Jackson将控制层返回的Long型数据转换成String类型，避免精度丢失
     *
     * @return org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
     * @author 王强
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return j -> j.serializerByType(Long.class, new ToStringSerializer());
    }

    /**
     * 添加拦截器
     *
     * @param registry 注册表
     * @author 王强
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/","/css/**","fonts/**","/images/**","/js/**");
    }

    /**
     * 密码编码器
     *
     * @return org.springframework.security.crypto.password.PasswordEncoder
     * @author 王强
     */
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
