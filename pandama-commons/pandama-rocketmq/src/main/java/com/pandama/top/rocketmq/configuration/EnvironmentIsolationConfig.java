package com.pandama.top.rocketmq.configuration;

import com.sun.istack.internal.NotNull;
import org.apache.rocketmq.spring.support.DefaultRocketMQListenerContainer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @description: 环境隔离配置
 * @author: 王强
 * @dateTime: 2023-06-12 21:30:18
 */
@Configuration
public class EnvironmentIsolationConfig implements BeanPostProcessor {

    @Resource
    private RocketEnhanceProperties properties;

    /**
     * 在装载Bean之前实现参数修改
     */
    @Override
    public Object postProcessBeforeInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
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
