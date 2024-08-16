package com.pandama.top.app.consumer.kafka;

import com.pandama.top.kafka.consumer.MultiThreadedConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.ArrayList;
import java.util.Map;

/**
 * Kafka消费者(多线程消费)
 *
 * @author 王强
 * @date 2023-07-08 11:55:31
 */
@Slf4j
//@Component
public class MultiThreadKafkaConsumer implements ApplicationRunner {

    private final KafkaProperties kafkaProperties;

    public MultiThreadKafkaConsumer(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Override
    @SuppressWarnings("all")
    public void run(ApplicationArguments args) throws Exception {
        Map<String, Object> properties = kafkaProperties.buildConsumerProperties();
        if (properties.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG) instanceof ArrayList) {
            String brokerId = String.join(",", (ArrayList) properties.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
            MultiThreadedConsumer consumer = new MultiThreadedConsumer(brokerId, "test1", String.valueOf(properties.get(ConsumerConfig.GROUP_ID_CONFIG)));
            consumer.run();
        }
    }
}
