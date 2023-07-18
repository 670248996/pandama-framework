package com.pandama.top.kafka.worker;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 消费者工人
 *
 * @author 王强
 * @date 2023-07-15 20:59:01
 */
public class ConsumerWorker<K, V> {

    /**
     * 同一个分区消费到的数据
     */
    private final List<ConsumerRecord<K, V>> recordsOfSamePartition;
    /**
     * 控制任务是否开始消费
     */
    private volatile boolean started = false;
    /**
     * 控制任务是否停止消费
     */
    private volatile boolean stopped = false;
    /**
     * 可从入锁
     */
    private final ReentrantLock lock = new ReentrantLock();
    /**
     * 无效的偏移量 offset
     */
    private final long INVALID_COMMITTED_OFFSET = -1L;
    /**
     * 保存该Worker当前已消费的最新位移。
     */
    private final AtomicLong latestProcessedOffset = new AtomicLong(INVALID_COMMITTED_OFFSET);
    /**
     * 使用CompletableFuture来保存Worker要提交的位移。
     */
    private final CompletableFuture<Long> future = new CompletableFuture<>();

    public ConsumerWorker(List<ConsumerRecord<K, V>> recordsOfSamePartition) {
        this.recordsOfSamePartition = recordsOfSamePartition;
    }

    public boolean run() {
        lock.lock();
        /**
         * 如果当前worker已经停止了，就不再处理
         */
        if (stopped) {
            return false;
        }
        started = true;
        lock.unlock();
        for (ConsumerRecord<K, V> record : recordsOfSamePartition) {
            if (stopped) {
                break;
            }
            /**
             * 处理消费到的数据，此处进行业务操作
             */
            handleRecord(record);
            /**
             * 处理完成后对偏移量 offset + 1
             */
            if (latestProcessedOffset.get() < record.offset() + 1) {
                latestProcessedOffset.set(record.offset() + 1);
            }
        }
        /**
         * 通过CompletableFuture来保存当前的消费偏移量 offset
         */
        return future.complete(latestProcessedOffset.get());
    }

    /**
     * 获取最新偏移量 offset
     *
     * @return long
     * @author 王强
     */
    public long getLatestProcessedOffset() {
        return latestProcessedOffset.get();
    }

    /**
     * 处理消费数据
     *
     * @param record 记录
     * @author 王强
     */
    private void handleRecord(ConsumerRecord<K, V> record) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }

        System.out.println(Thread.currentThread().getName() + " finished message processed. Record partition = " + record.partition() + " offset = " + record.offset());
    }

    /**
     * 关闭
     *
     * @author 王强
     */
    public void close() {
        lock.lock();
        this.stopped = true;
        if (!started) {
            future.complete(latestProcessedOffset.get());
        }
        lock.unlock();
    }

    /**
     * 是否完成
     *
     * @return boolean
     * @author 王强
     */
    public boolean isFinished() {
        return future.isDone();
    }

    /**
     * 等待完成
     *
     * @param timeout  超时
     * @param timeUnit 时间单位
     * @return long
     * @author 王强
     */
    public long waitForCompletion(long timeout, TimeUnit timeUnit) {
        try {
            return future.get(timeout, timeUnit);
        } catch (Exception e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return INVALID_COMMITTED_OFFSET;
        }
    }
}
