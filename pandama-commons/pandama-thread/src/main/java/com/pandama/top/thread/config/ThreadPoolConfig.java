package com.pandama.top.thread.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义手动调用线程池
 *
 * @author 王强
 * @date 2023-07-08 15:36:04
 */
@Slf4j
public class ThreadPoolConfig {

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 1000;
    private static final String THREAD_NAME = "pandama-thread-";

    /**
     * 初始化线程池
     *
     * @return java.util.concurrent.Executor
     * @author 王强
     */
    public static Executor initThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(CORE_POOL_SIZE);
        //配置最大线程数
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        //配置队列大小
        executor.setQueueCapacity(QUEUE_CAPACITY);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(THREAD_NAME);
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }
}
