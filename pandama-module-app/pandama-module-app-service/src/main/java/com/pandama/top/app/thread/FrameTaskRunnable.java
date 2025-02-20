package com.pandama.top.app.thread;

import com.alibaba.fastjson.support.geo.Geometry;
import lombok.extern.slf4j.Slf4j;

/**
 * 抽帧任务处理线程
 *
 * @author 王强
 * @date 2024-08-24 22:03:12
 */
@Slf4j
public abstract class FrameTaskRunnable implements Runnable {
    protected String result = "";

    protected final Thread heartbeatThread;

    @SuppressWarnings("all")
    public FrameTaskRunnable() {
        heartbeatThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(3000);
                    heartbeat();
                } catch (InterruptedException e) {
                    log.info("心跳结束: {}", Thread.currentThread().getName());
                    break;
                }
            }
        });
    }

    @Override
    @SuppressWarnings("all")
    public void run() {
        try {
            heartbeatThread.setDaemon(true);
            String request = executeBefore();
            heartbeatThread.start();
            result = doExecute(request);
            executeAfter(result);
        } catch (Exception e) {
            executeError(e);
        } finally {
            heartbeatThread.interrupt();
            executeFinally();
        }
    }

    /**
     * 执行前
     *
     * @return com.pactera.vision.detection.api.model.view.OfflineAnalysisVO
     * @author 王强
     */
    public abstract String executeBefore();

    /**
     * 心跳
     *
     * @author 王强
     */
    public abstract void heartbeat();

    /**
     * 执行
     *
     * @param request 请求
     * @return com.pactera.vision.detection.api.model.view.OfflineAnalysisResultVO
     * @throws Exception 异常
     * @author 王强
     */
    public abstract String doExecute(String request) throws Exception;

    /**
     * 执行后
     *
     * @param result 结果
     * @author 王强
     */
    public void executeAfter(String result) {}

    /**
     * 执行错误
     *
     * @param e e
     * @author 王强
     */
    public abstract void executeError(Exception e);

    /**
     * 最后执行
     *
     * @author 王强
     */
    public abstract void executeFinally();
}
