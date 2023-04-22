package com.pandama.top.thread.model;

import com.pandama.top.pojo.vo.TaskVo;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * @description: 生产者-消费者线程模型（使用非阻塞队列，CAS+自旋实现）
 * @author: 白剑民
 * @dateTime: 2022/6/29 09:34
 */
@Slf4j
public class ThreadNoBlockingModel {

    /**
     * 定义非阻塞队列
     */
    private final Queue<TaskVo> queue = new ConcurrentLinkedQueue<>();

    /**
     * @description: 获取当前队列大小
     * @author: 白剑民
     * @date: 2022-07-02 15:09:54
     * @return: int
     * @version: 1.0
     */
    public int getQueueSize() {
        return queue.size();
    }

    /**
     * @description: 生产者
     * @author: 白剑民
     * @dateTime: 2022/6/29 09:34
     */
    public class Producer implements Runnable {
        // 任务对象
        private final TaskVo t;

        public Producer(TaskVo t) {
            this.t = t;
        }

        @Override
        public void run() {
            // 任务对象放入队列
            queue.offer(t);
        }
    }

    /**
     * @description: 消费者
     * @author: 白剑民
     * @dateTime: 2022/6/29 09:34
     */
    public class Consumer implements Runnable {

        /**
         * 任务执行逻辑
         */
        private final java.util.function.Consumer<TaskVo> consumer;
        /**
         * 由外部统一传入一个线程计数器，用于统计消费者线程退出情况
         * 如果没有阻塞主线程的需要，则无需传入
         */
        private final CountDownLatch count;

        public Consumer(java.util.function.Consumer<TaskVo> consumer, CountDownLatch count) {
            this.consumer = consumer;
            this.count = count;
        }

        @Override
        public void run() {
            // 初始化任务完成标志
            boolean done = false;
            // 长轮询消费
            while (!done) {
                try {
                    // 取出任务对象，不阻塞队列
                    TaskVo t = queue.poll();
                    // 取到任务对象终止信号量发出时，终止当前线程
                    if (t != null) {
                        if (t.isEmpty()) {
                            // 继续往队列中放入终止信号量，以告知其他消费者线程退出
                            queue.offer(t);
                            // 任务完成标志回拨，终止当前任务
                            done = true;
                            // 如果主线程需要等待所有消费者的消费结果
                            // 所以按定义的消费者线程组大小，在每个消费者线程退出时进行count减一操作，直至所有消费者退出，释放主线程
                            if (count != null) {
                                count.countDown();
                                // 在最后一个线程释放时清空队列
                                if (count.getCount() == 0) {
                                    // 队列任务对象取完后清空一次队列(队列中实际上仍然包含着最后被put进去的带着终止信号量的任务对象)
                                    queue.clear();
                                }
                            }
                        } else {
                            // 执行处理逻辑
                            consumer.accept(t);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("非阻塞队列实现消费者出现执行错误: {}", e.getMessage());
                }
            }
        }
    }
}
