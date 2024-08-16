package com.pandama.top.app.consumer.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * Kafka消费者(普通注解模式消费)
 *
 * @author 王强
 * @date 2023-07-08 11:55:31
 */
@Slf4j
//@Component
public class CommonKafkaConsumer {

    @KafkaListener(topics = {"test1"})
    public void run(ConsumerRecord<?, ?> record) {
        log.info("获取到的抽帧图片: {}", record.value().toString());
    }
}
