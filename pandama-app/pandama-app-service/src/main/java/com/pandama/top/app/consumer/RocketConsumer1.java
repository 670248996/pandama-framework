package com.pandama.top.app.consumer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * Rocket消费者
 *
 * @author 王强
 * @date 2023-07-08 11:55:31
 */
@Slf4j
//@Component
//@RocketMQMessageListener(topic = "app_topic1", consumerGroup = "app_consumer_group1", consumeThreadNumber = 1)
public class RocketConsumer1 implements RocketMQListener<String> {

    @SneakyThrows
    @Override
    public void onMessage(String message) {
        // 处理消息的逻辑
        log.info("普通消费者 接收到消息: " + message);
        Thread.sleep(100);
    }
}
