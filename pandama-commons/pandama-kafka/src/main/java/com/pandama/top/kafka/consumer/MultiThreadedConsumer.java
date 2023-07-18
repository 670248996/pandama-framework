package com.pandama.top.kafka.consumer;

import com.pandama.top.kafka.worker.ConsumerWorker;
import com.pandama.top.kafka.listener.MultiThreadedRebalanceListener;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.*;

/**
 * 多消费者线程
 *
 * @author 王强
 * @date 2023-07-15 20:58:11
 */
public class MultiThreadedConsumer {

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
    private final static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2, r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    public MultiThreadedConsumer(String brokerId, String topic, String groupId) {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerId);
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic), new MultiThreadedRebalanceListener(consumer, outstandingWorkers, offsetsToCommit));
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
            CompletableFuture.supplyAsync(worker::run, executor);
            outstandingWorkers.put(tp, worker);
        });
        /**
         * 暂停消费者消费该分区
         */
        consumer.pause(pausedPartitions);
    }
}
