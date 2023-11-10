package com.pandama.top.app.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka消费者(普通模式消费)
 *
 * @author 王强
 * @date 2023-07-08 11:55:31
 */
@Slf4j
//@Component
public class KafkaConsumer3 {

    @KafkaListener(topics = {"test1"})
    public void run(ConsumerRecord<?, ?> record) {
        log.info("获取到的抽帧图片: {}", record.value().toString());
    }
}
