package com.pandama.top.logRecord.thread;

import com.pandama.top.logRecord.configuration.LogRecordProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * 日志记录线程池
 *
 * @author 王强
 * @date 2023-07-08 15:24:55
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "log-record.thread-pool.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({LogRecordProperties.class})
public class LogRecordThreadPool {

    private static final ThreadFactory THREAD_FACTORY = new CustomizableThreadFactory("log-record-");

    private final ExecutorService LOG_RECORD_POOL_EXECUTOR;

    public LogRecordThreadPool(LogRecordProperties logRecordProperties) {
        log.info("LogRecordThreadPool init poolSize [{}]", logRecordProperties.getThreadPool().getPoolSize());
        int poolSize = logRecordProperties.getThreadPool().getPoolSize();
        this.LOG_RECORD_POOL_EXECUTOR = new ThreadPoolExecutor(
                poolSize, poolSize, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024), THREAD_FACTORY, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public ExecutorService getLogRecordPoolExecutor() {
        return LOG_RECORD_POOL_EXECUTOR;
    }
}
