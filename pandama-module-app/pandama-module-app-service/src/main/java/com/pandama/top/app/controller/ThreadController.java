package com.pandama.top.app.controller;

import com.pandama.top.app.thread.MyTaskRunnable;
import com.pandama.top.core.global.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 设备控制器
 *
 * @author 王强
 * @date 2023-07-08 11:55:46
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/thread")
public class ThreadController {

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1));

    @PreAuthorize("@ss.hasPermi('monitor:server:list')")
    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public Response<?> start(@RequestParam("id") String id) {
        CompletableFuture.runAsync(new MyTaskRunnable() {
            @Override
            public String executeBefore() {
                log.info("创建任务: {}", id);
                heartbeatThread.setName("Offline-Analysis-Heartbeat-Image-" + id);
                return "request";
            }

            @Override
            public void heartbeat() {
                if (StringUtils.isBlank(result)) {
                    log.info("任务心跳: {}", id);
                }
            }

            @Override
            public String doExecute(String request) throws Exception {
                log.info("执行任务: {}", id);
                Thread.sleep(10000);
                return "success";
            }

            @Override
            public void executeError(Exception e) {
                result = "false";
                log.error("任务异常: {}", id);
                e.printStackTrace();
            }

            @Override
            public void executeFinally() {
                log.info("任务结束: {}", id);
            }
        }, executor);
        return Response.success();
    }
}
