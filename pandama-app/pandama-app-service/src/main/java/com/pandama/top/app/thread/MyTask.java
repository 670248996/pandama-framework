package com.pandama.top.app.thread;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.Data;

/**
 * 任务
 *
 * @author 王强
 * @date 2024-08-24 22:03:12
 */
@Data
public abstract class MyTask implements Runnable {

    public MyTask(String taskId) {
        this.taskId = taskId;
    }

    private String taskId;

    private String securityId = IdWorker.getIdStr();

    @Override
    @SuppressWarnings("all")
    public void run() {
        Thread heartbeatThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // 心跳
                    System.out.println("发送心跳...");
                    Thread.sleep(1000);
                    heartbeat();
                } catch (InterruptedException e) {
                    System.out.println("Heartbeat sender thread was interrupted.");
                    // 当线程被中断时，‌退出循环
                    break;
                }
            }
        });
        try {
            heartbeatThread.setDaemon(true);
            if (!executeBefore()) {
                System.out.println("任务" + taskId + "已存在");
                throw new RuntimeException();
            }
            heartbeatThread.start();
            System.out.println("执行业务逻辑");
            doExecute();
            Thread.sleep(10000);
            System.out.println("Main thread is ending.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            heartbeatThread.interrupt();
        }
    }

    /**
     * 执行
     *
     * @return java.lang.String
     * @author 王强
     */
    public abstract boolean executeBefore();

    /**
     * 心跳
     *
     * @author 王强
     */
    public abstract boolean heartbeat();

    /**
     * 执行
     *
     * @return java.lang.String
     * @author 王强
     */
    public abstract String doExecute();
}
