package com.pandama.top.kafka.consumer;

import org.slf4j.MDC;

import java.util.Map;

/**
 * 自定义Runnable
 *
 * @author 王强
 * @date 2024-08-09 11:44:32
 */
public abstract class CustomerRunnable<T extends Object, R> implements Runnable {

    private volatile Map<String, String> contentMap;

    public CustomerRunnable() {
        this.contentMap = MDC.getCopyOfContextMap();
    }

    @Override
    public void run() {
        try {
            // 初始化
            initContentMap();
            // 任务执行前
            T t = doExecuteBefore();
            // 执行任务
            R r = doExecute(t);
            // 任务执行后
            doExecuteAfter(t, r);
        } catch (Exception e) {
            // 处理一次
            handleError(e);
        } finally {
            // 清理
            clearContentMap();
        }
    }

    /**
     * 在执行之前执行
     *
     * @return T
     * @author 王强
     */
    protected abstract T doExecuteBefore();

    /**
     * 执行
     *
     * @param t t
     * @return R
     * @author 王强
     */
    protected abstract R doExecute(T t);

    /**
     * 在之后执行
     *
     * @param t t
     * @param r r
     * @return void
     * @author 王强
     */
    protected abstract void doExecuteAfter(T t, R r);

    /**
     * 处理错误
     *
     * @param e e
     * @return void
     * @author 王强
     */
    protected abstract void handleError(Throwable e);

    private void initContentMap() {
        if (contentMap == null) {
            MDC.clear();
        } else {
            MDC.setContextMap(this.contentMap);
        }
    }

    private void clearContentMap() {
        MDC.clear();
    }
}
