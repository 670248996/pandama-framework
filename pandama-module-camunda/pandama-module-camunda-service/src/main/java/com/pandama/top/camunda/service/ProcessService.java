package com.pandama.top.camunda.service;

import org.camunda.bpm.engine.task.Task;

import java.util.Map;
import java.util.function.Consumer;

/**
 * 过程
 *
 * @author 王强
 * @date 2024-09-14 09:18:06
 */
public interface ProcessService {

    void start(String definitionId, Map<String, Object> variables, Consumer<Task> consumer);

    void approve(String taskId, Map<String, Object> variables, Consumer<Task> consumer);

    void reject(String taskId, Map<String, Object> variables, Consumer<Task> consumer);

    void reback(String taskId, Consumer<Task> consumer);

    void suspend(String instanceId);

    void activate(String instanceId);

    void delete(String instanceId);
}