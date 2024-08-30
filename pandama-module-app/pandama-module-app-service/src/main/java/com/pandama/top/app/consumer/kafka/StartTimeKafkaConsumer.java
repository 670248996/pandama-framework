package com.pandama.top.app.consumer.kafka;

import cn.hutool.core.date.DateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.core.env.Environment;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Kafka消费者(指定时间开始消费)
 *
 * @author 王强
 * @date 2023-07-08 11:55:31
 */
@Slf4j
//@Component
public class StartTimeKafkaConsumer implements ApplicationRunner {

    private final KafkaProperties kafkaProperties;
    private final Environment environment;

    public StartTimeKafkaConsumer(KafkaProperties kafkaProperties, Environment environment) {
        this.kafkaProperties = kafkaProperties;
        this.environment = environment;
    }

    @Override
    @SuppressWarnings("all")
    public void run(ApplicationArguments args) throws Exception {
        // 通过配置文件或者启动参数指定
        String property = environment.getProperty("kafka.consumer.startTime");
        String topic = environment.getProperty("kafka.consumer.topic");
        if (StringUtils.isAnyBlank(property, topic)) {
            log.info("未指定 消费主题 kafka.consumer.topic 和 起始消费时间 kafka.consumer.startTime");
            return;
        }
        Map<String, Object> properties = kafkaProperties.buildConsumerProperties();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        // 获取topic的partition信息
        List<PartitionInfo> partitionInfos = consumer.partitionsFor(topic);
        List<TopicPartition> topicPartitions = new ArrayList<>();
        Map<TopicPartition, Long> timestampsToSearch = new HashMap<>();
        long startTime = new DateTime(property).toInstant().toEpochMilli();
        for (PartitionInfo partitionInfo : partitionInfos) {
            topicPartitions.add(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()));
            timestampsToSearch.put(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()), startTime);
        }
        consumer.assign(topicPartitions);
        // 获取每个partition今天凌晨的偏移量
        Map<TopicPartition, OffsetAndTimestamp> map = consumer.offsetsForTimes(timestampsToSearch);
        OffsetAndTimestamp offsetTimestamp = null;
        log.info("开始设置各分区初始偏移量...");
        for (Map.Entry<TopicPartition, OffsetAndTimestamp> entry : map.entrySet()) {
            // 如果设置的查询偏移量的时间点大于最大的索引记录时间，那么value就为空
            offsetTimestamp = entry.getValue();
            if (offsetTimestamp != null) {
                int partition = entry.getKey().partition();
                long timestamp = offsetTimestamp.timestamp();
                long offset = offsetTimestamp.offset();
                log.info("设置初始偏移量 分区:{}, time:{}, offset:{}", partition, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp)), offset);
                // 设置读取消息的偏移量
                consumer.seek(entry.getKey(), offset);
            }
        }

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            // 定义 CompletableFuture 任务集合
            List<CompletableFuture<Void>> futures = new ArrayList<>(records.count());
//            log.info("records.size:[{}]", records.count());
            // 遍历每组任务分析结果
            records.forEach(record -> {
                // 创建 CompletableFuture 对象
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    String messageTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(record.timestamp()));
                    if (record.value().contains("1823616908444831745")) {
                        log.info("time:[{}], partition:[{}], offset:[{}], message:[{}]", messageTime, record.partition(), record.offset(), record.value());
                    }
                });
                // 将 CompletableFuture 任务放到集合中
                futures.add(future);
            });
            // 等待所有的异步任务完成
            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allOf.join();
        }
    }
}
