package com.pandama.top.app.factory;

import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyFactoryConfig {

    /**
     * 服务定位器工厂Bean
     *
     * @return org.springframework.beans.factory.config.ServiceLocatorFactoryBean
     * @author 王强
     */
    @Bean
    public ServiceLocatorFactoryBean serviceLocatorFactoryBean() {
        ServiceLocatorFactoryBean serviceLocatorFactoryBean = new ServiceLocatorFactoryBean();
        serviceLocatorFactoryBean.setServiceLocatorInterface(PaymentFactory.class);
        return serviceLocatorFactoryBean;
    }
}
