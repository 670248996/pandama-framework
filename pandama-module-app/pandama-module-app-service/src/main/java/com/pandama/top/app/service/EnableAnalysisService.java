package com.pandama.top.app.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 启用分析服务
 *
 * @author 王强
 * @date 2024-09-05 22:00:30
 */
@Slf4j
@Service
@ExternalTaskSubscription(topicName = "enable_analysis", processDefinitionKeyIn = {"Process_analysis"})
public class EnableAnalysisService implements ExternalTaskHandler {

    public static AtomicInteger integer = new AtomicInteger(0);

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        int i = integer.incrementAndGet();
        log.info("启动分析");
        if (i % 2 == 0) {
            externalTaskService.handleFailure(externalTask, "分析失败原因", "分析失败详情", 0, 0);
        } else {
            externalTaskService.complete(externalTask);
        }
    }
}
