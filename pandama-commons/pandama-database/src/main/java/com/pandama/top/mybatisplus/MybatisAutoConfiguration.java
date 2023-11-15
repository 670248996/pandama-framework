package com.pandama.top.mybatisplus;

import com.pandama.top.mybatisplus.interceptor.MybatisSqlInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @description: mybatis自动配置
 * @author: 王强
 * @dateTime: 2023-02-16 16:36:43
 */
@Configuration
public class MybatisAutoConfiguration implements InitializingBean {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @Override
    public void afterPropertiesSet() {
        MybatisSqlInterceptor mybatisInterceptor = new MybatisSqlInterceptor();
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
            configuration.addInterceptor(mybatisInterceptor);
        }
    }
}
