package com.pandama.top.user;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 我应用程序上下文初始化器
 *
 * @author 王强
 * @date 2023-07-08 15:58:02
 */
public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // 注意，如果你同时还使用了 spring cloud，这里需要做个判断，要不要在 spring cloud applicationContext 中做这个事
        // 通常 spring cloud 中的 bean 都和业务没关系，是需要跳过的
        applicationContext.addBeanFactoryPostProcessor(new MyBeanDefinitionRegistryPostProcessor());
    }
}
