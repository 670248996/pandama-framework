package com.pandama.top.app.controller;

import com.pandama.top.app.thread.MyTask;
import com.pandama.top.core.global.response.Response;
import com.pandama.top.redis.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ArrayBlockingQueue;
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

    @Autowired
    private RedisUtils redisUtils;

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public Response<?> start() {
        MyTask myTask1 = new MyTask("task1") {
            @Override
            public boolean executeBefore() {
                return redisUtils.tryReentrantLock(getTaskId(), getSecurityId(), 1000, 5000);
            }

            @Override
            public boolean heartbeat() {
                return redisUtils.tryReentrantLock(getTaskId(), getSecurityId(), 1000, 5000);
            }

            @Override
            public String doExecute() {
                System.out.println("task1运行中");
                return null;
            }
        };

        MyTask myTask2 = new MyTask("task1") {
            @Override
            public boolean executeBefore() {
                return redisUtils.tryReentrantLock(getTaskId(), getSecurityId(), 1000, 5000);
            }

            @Override
            public boolean heartbeat() {
                return redisUtils.tryReentrantLock(getTaskId(), getSecurityId(), 1000, 5000);
            }

            @Override
            public String doExecute() {
                System.out.println("task2运行中");
                return null;
            }
        };

        executor.submit(myTask1);
        executor.submit(myTask2);
        return Response.success();
    }
}
