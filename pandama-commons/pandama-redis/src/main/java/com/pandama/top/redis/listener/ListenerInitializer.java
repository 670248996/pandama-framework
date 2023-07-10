package com.pandama.top.redis.listener;

import com.pandama.top.redis.annotation.RedisListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.MessageListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 广播监听器初始化
 *
 * @author 王强
 * @date 2023-07-10 14:34:39
 */
@Slf4j
public class ListenerInitializer implements InitializingBean {

    /**
     * 频道名称与监听实现类的映射
     */
    private Map<String, MessageListener> listeners;

    private final ApplicationContext applicationContext;

    public ListenerInitializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        // 获取所有实现
        Map<String, MessageListener> listenerImpls = applicationContext.getBeansOfType(MessageListener.class);
        // 定义频道名称与监听实现类的映射
        Map<String, MessageListener> listeners = new HashMap<>(16);
        listenerImpls.forEach((s, messageListener) -> {
            RedisListener channel = messageListener.getClass().getAnnotation(RedisListener.class);
            if (channel == null) {
                throw new RuntimeException(messageListener.getClass().getSimpleName() + "实现了MessageListener接口，但是类上没有标注@RedisListener注解以注明监听频道");
            } else {
                log.info("Redis监听器注册成功 监听频道: " + channel.value());
                listeners.put(channel.value(), messageListener);
            }
        });
        this.listeners = listeners;
    }

    /**
     * 获取所有频道名称与监听实现类的映射
     *
     * @return java.util.Map<java.lang.String, org.springframework.data.redis.connection.MessageListener>
     * @author 王强
     */
    public Map<String, MessageListener> getListeners() {
        return listeners;
    }
}
