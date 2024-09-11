package com.pandama.top.app.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.stereotype.Service;

/**
 * 算法分析服务
 *
 * @author 王强
 * @date 2024-09-05 22:00:33
 */
@Slf4j
@Service
@ExternalTaskSubscription(topicName = "algorithm_analysis", processDefinitionKeyIn = {"Process_analysis"})
public class AlgorithmAnalysisService implements ExternalTaskHandler {
    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        log.info("算法分析");
        externalTaskService.complete(externalTask);
    }
}
