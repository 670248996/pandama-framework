package com.pandama.top.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @description: mybatis-plus配置类
 * @author: 白剑民
 * @dateTime: 2022/7/8 16:34
 */
@Configuration
@EnableTransactionManagement
public class MybatisPlusAutoConfiguration {
    /**
     * @description: 乐观锁及分页插件配置
     * @author: 白剑民
     * @date: 2022-07-08 16:37:34
     * @return: com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
     * @version: 1.0
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
