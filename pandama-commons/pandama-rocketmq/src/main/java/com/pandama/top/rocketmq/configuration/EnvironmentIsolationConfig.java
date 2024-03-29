package com.pandama.top.rocketmq.configuration;

import org.apache.rocketmq.spring.support.DefaultRocketMQListenerContainer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 环境隔离配置
 *
 * @author 王强
 * @date 2023-07-08 15:29:11
 */
@Configuration
public class EnvironmentIsolationConfig implements BeanPostProcessor {

    @Resource
    private RocketEnhanceProperties properties;

    /**
     * 在装载Bean之前实现参数修改
     *
     * @param bean     Bean
     * @param beanName Bean名称
     * @return java.lang.Object
     * @author 王强
     */
    @Override
    @SuppressWarnings("all")
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DefaultRocketMQListenerContainer) {
            DefaultRocketMQListenerContainer container = (DefaultRocketMQListenerContainer) bean;
            // 拼接Topic
            if (properties.isEnabledIsolation() && StringUtils.hasText(properties.getEnvironment())) {
                container.setTopic(String.join("_", container.getTopic(), properties.getEnvironment()));
            }
            return container;
        }
        return bean;
    }
}
