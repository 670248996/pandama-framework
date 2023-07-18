package com.pandama.top.kafka.listener;

import com.pandama.top.kafka.worker.ConsumerWorker;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 多线程分区重平衡侦听器
 *
 * @author 王强
 * @date 2023-07-15 21:02:46
 */
public class MultiThreadedRebalanceListener implements ConsumerRebalanceListener {

    /**
     * 消费者
     */
    private final Consumer<String, String> consumer;
    /**
     * 各分区在执行的所有任务
     */
    private final Map<TopicPartition, ConsumerWorker<String, String>> partitionWorkers;
    /**
     * 各分区待提交偏移量
     */
    private final Map<TopicPartition, OffsetAndMetadata> partitionOffsets;

    public MultiThreadedRebalanceListener(Consumer<String, String> consumer,
                                          Map<TopicPartition, ConsumerWorker<String, String>> partitionWorkers,
                                          Map<TopicPartition, OffsetAndMetadata> partitionOffsets) {
        this.consumer = consumer;
        this.partitionWorkers = partitionWorkers;
        this.partitionOffsets = partitionOffsets;
    }

    /**
     * 调用时机就是consumer停止拉取数据之后，Rebalance开始之前
     * 我们可以在此方法中提交位移，避免rebalance导致重复消费
     *
     * @param partitions 分区
     * @author 王强
     */
    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        Map<TopicPartition, ConsumerWorker<String, String>> stoppedWorkers = new HashMap<>(16);
        for (TopicPartition tp : partitions) {
            /**
             * 关闭 outstandingWorkers 中 tp 分区的任务
             */
            ConsumerWorker<String, String> worker = partitionWorkers.remove(tp);
            if (worker != null) {
                worker.close();
                stoppedWorkers.put(tp, worker);
            }
        }

        /**
         * 获取关闭分区任务当前的偏移量 offset
         */
        stoppedWorkers.forEach((tp, worker) -> {
            long offset = worker.waitForCompletion(1, TimeUnit.SECONDS);
            if (offset > 0L) {
                partitionOffsets.put(tp, new OffsetAndMetadata(offset));
            }
        });

        /**
         * 封装关闭分区的偏移量信息
         */
        Map<TopicPartition, OffsetAndMetadata> revokedOffsets = new HashMap<>(16);
        partitions.forEach(tp -> {
            OffsetAndMetadata offset = partitionOffsets.remove(tp);
            if (offset != null) {
                revokedOffsets.put(tp, offset);
            }
        });

        try {
            /**
             * 提交关闭分区的偏移量
             */
            consumer.commitSync(revokedOffsets);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用时机在rebalance之后，consumer拉取消息之前，我们在此方法中调整offset的值。
     *
     * @param partitions 分区
     * @author 王强
     */
    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        consumer.resume(partitions);
    }
}