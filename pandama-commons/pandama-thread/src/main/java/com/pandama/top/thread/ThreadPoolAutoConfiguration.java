package com.pandama.top.thread;

import com.pandama.top.thread.config.CustomThreadProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程执行器自动配置类
 *
 * @author 王强
 * @date 2023-07-08 15:36:41
 */
@Slf4j
@Configuration
@EnableAsync
@EnableConfigurationProperties(CustomThreadProperties.class)
public class ThreadPoolAutoConfiguration {

    /**
     * 注入自定义配置
     */
    private final CustomThreadProperties properties;

    public ThreadPoolAutoConfiguration(CustomThreadProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Executor asyncServiceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(properties.getCorePoolSize());
        //配置最大线程数
        executor.setMaxPoolSize(properties.getMaxPoolSize());
        //配置队列大小
        executor.setQueueCapacity(properties.getQueueCapacity());
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(properties.getThreadName());
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }
}
