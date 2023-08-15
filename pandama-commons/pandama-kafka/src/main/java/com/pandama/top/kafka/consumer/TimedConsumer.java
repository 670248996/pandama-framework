package com.pandama.top.kafka.consumer;

import com.pandama.top.kafka.worker.ConsumerWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 多消费者线程
 *
 * @author 王强
 * @date 2023-07-15 20:58:11
 */
@Slf4j
public class TimedConsumer {

    /**
     * 分区和任务执行对象 映射表处理
     */
    private final Map<TopicPartition, ConsumerWorker<String, String>> outstandingWorkers = new HashMap<>();
    /**
     * 分区和偏移量 offset 映射表
     */
    private final Map<TopicPartition, OffsetAndMetadata> offsetsToCommit = new HashMap<>();
    /**
     * 最后提交时间
     */
    private long lastCommitTime = System.currentTimeMillis();
    /**
     * 消费者
     */
    private final Consumer<String, String> consumer;
    /**
     * 默认提交时间间隔
     */
    private final int DEFAULT_COMMIT_INTERVAL = 3000;
    /**
     * 当前消费的offset
     */
    private final Map<TopicPartition, Long> currentConsumedOffsets = new HashMap<>();
    /**
     * 定义业务处理线程池并设置为守护线程（线程池的线程数将指定为N倍于CPU核心数的值）
     */
    private final static Executor EXECUTOR = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2, r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    public TimedConsumer(Map<String, Object> props, String topic, LocalDateTime startDateTime) {
        consumer = new KafkaConsumer<>(props);
        // 获取topic的partition信息
        List<PartitionInfo> partitionInfos = consumer.partitionsFor(topic);
        List<TopicPartition> topicPartitions = new ArrayList<>();
        Map<TopicPartition, Long> timestampsToSearch = new HashMap<>();
        long startTime = startDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
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
                log.info("设置分区初始偏移量 分区:{}, time:{}, offset:{}", partition, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp)), offset);
                // 设置读取消息的偏移量
                consumer.seek(entry.getKey(), offset);
            }
        }
    }

    public void run() {
        try {
            while (true) {
                /**
                 * 消费者手动拉取消息
                 */
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                /**
                 * 消息按照分区，分发到不同线程执行
                 */
                distributeRecords(records);
                /**
                 * 检查任务完成情况
                 */
                checkOutstandingWorkers();
                /**
                 * 提交位移
                 */
                commitOffsets();
            }
        } finally {
            consumer.close();
        }
    }

    /**
     * 对已完成消息处理并提交位移的分区执行恢复操作
     *
     * @author 王强
     */
    private void checkOutstandingWorkers() {
        /**
         * 已完成消息消费的分区
         */
        Set<TopicPartition> completedPartitions = new HashSet<>();
        /**
         * 遍历处理中任务
         */
        outstandingWorkers.forEach((tp, worker) -> {
            /**
             * 判断任务是否处理结束
             * 如果任务处理结束，则将该分区添加到completedPartitions中
             */
            if (worker.isFinished()) {
                completedPartitions.add(tp);
            }
            /**
             * 获取任务中最后的偏移量 offset
             */
            long offset = worker.getLatestProcessedOffset();
            /**
             * 设置当前消费者的偏移量 offset
             */
            currentConsumedOffsets.put(tp, offset);
            /**
             * 设置分区待提交的偏移量 offset
             */
            if (offset > 0L) {
                offset = Math.max(10000, offset);
                offsetsToCommit.put(tp, new OffsetAndMetadata(offset));
            }
        });
        /**
         * 从处理中任务移除已完成的任务
         */
        completedPartitions.forEach(outstandingWorkers::remove);
        /**
         * 恢复消费者消费该分区
         */
        consumer.resume(completedPartitions);
    }

    /**
     * 提交偏移量 offset
     *
     * @author 王强
     */
    private void commitOffsets() {
        try {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastCommitTime > DEFAULT_COMMIT_INTERVAL && !offsetsToCommit.isEmpty()) {
                /**
                 * 同步提交偏移量
                 */
                consumer.commitSync(offsetsToCommit);
                offsetsToCommit.forEach((partition, offset) -> {
                    log.info("同步提交偏移量 分区:{}, offset:{}", partition.partition(), offset.offset());
                });
                offsetsToCommit.clear();
            }
            lastCommitTime = currentTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将不同分区的消息交由不同的线程，同时暂停该分区消息消费
     *
     * @param records 记录
     * @author 王强
     */
    private void distributeRecords(ConsumerRecords<String, String> records) {
        if (records.isEmpty()) {
            return;
        }
        /**
         * 暂停消费的分区
         */
        Set<TopicPartition> pausedPartitions = new HashSet<>();
        /**
         * 遍历各分区，将分区中的消息封装成ConsumerWorker任务对象，交给CompletableFuture执行
         */
        records.partitions().forEach(tp -> {
            /**
             * 拿到同一个分区下的记录
             */
            List<ConsumerRecord<String, String>> partitionedRecords = records.records(tp);
            pausedPartitions.add(tp);
            /**
             * 构建消费该分区的Worker
             */
            final ConsumerWorker<String, String> worker = new ConsumerWorker<>(partitionedRecords);
            /**
             * 在线程池中执行消息消费逻辑
             */
            CompletableFuture.supplyAsync(worker::run, EXECUTOR);
            outstandingWorkers.put(tp, worker);
        });
        /**
         * 暂停消费者消费该分区
         */
        consumer.pause(pausedPartitions);
    }
}
