package com.pandama.top.camunda.service.impl;

import com.pandama.top.camunda.service.ProcessService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

@Component
public class LeaveProcessServiceImpl implements ProcessService {

    @Override
    public void start(String definitionId, Map<String, Object> variables, Consumer<Task> consumer) {

    }

    @Override
    public void approve(String taskId, Map<String, Object> variables, Consumer<Task> consumer) {

    }

    @Override
    public void reject(String taskId, Map<String, Object> variables, Consumer<Task> consumer) {

    }

    @Override
    public void reback(String taskId, Consumer<Task> consumer) {

    }

    @Override
    public void suspend(String instanceId) {

    }

    @Override
    public void activate(String instanceId) {

    }

    @Override
    public void delete(String instanceId) {

    }
}
