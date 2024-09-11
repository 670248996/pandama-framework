package com.pandama.top.camunda.service;

import cn.hutool.core.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricDetail;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 实例任务服务
 *
 * @author 王强
 * @date 2024-09-06 21:56:17
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class CamundaTaskService {
    private final org.camunda.bpm.engine.TaskService taskService;
    private final HistoryService historyService;
    private final RuntimeService runtimeService;
    private final CamundaProcessService processInfoService;

    public List<Task> list(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    @Transactional(rollbackFor = Exception.class)
    public Task start(String definitionId, Map<String, Object> variables, Consumer<Task> consumer) {
        ProcessInstance instance = processInfoService.start(definitionId, null, variables);
        Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
        taskService.setVariables(task.getId(), variables);
        taskService.complete(task.getId());
        long count = runtimeService.createExecutionQuery().processInstanceId(task.getProcessInstanceId()).count();
        if (count == 0) {
            consumer.accept(task);
        }
        return task;
    }

    @Transactional(rollbackFor = Exception.class)
    public void approve(String taskId, Map<String, Object> variables, Consumer<Task> consumer) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Optional.ofNullable(task).orElseThrow(() -> new RuntimeException("任务【" + taskId + "】不存在"));
        taskService.setVariables(task.getId(), variables);
        taskService.complete(task.getId());
        long count = runtimeService.createExecutionQuery().processInstanceId(task.getProcessInstanceId()).count();
        if (count == 0) {
            consumer.accept(task);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void reject(String taskId, Map<String, Object> variables, Consumer<Task> consumer) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Optional.ofNullable(task).orElseThrow(() -> new RuntimeException("任务【" + taskId + "】不存在"));
        taskService.setVariables(task.getId(), variables);
        taskService.complete(task.getId());
        long count = runtimeService.createExecutionQuery().processInstanceId(task.getProcessInstanceId()).count();
        if (count == 0) {
            consumer.accept(task);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void reback(String taskId, Consumer<Task> consumer) {
        Task task = taskService.createTaskQuery().taskId(taskId).active().singleResult();
        String instanceId = task.getProcessInstanceId();
        List<HistoricTaskInstance> historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(instanceId).orderByHistoricActivityInstanceStartTime().desc().list();
        List<HistoricTaskInstance> historicTaskInstances = historicTaskInstance.stream()
                .filter(v -> !v.getTaskDefinitionKey().equals(task.getTaskDefinitionKey())).collect(Collectors.toList());
        Assert.notEmpty(historicTaskInstances, "当前已是初始任务！");
        HistoricTaskInstance curr = historicTaskInstances.get(0);
        runtimeService.createProcessInstanceModification(instanceId)
                .cancelAllForActivity(task.getTaskDefinitionKey())
                .setAnnotation("重新执行")
                .startBeforeActivity(curr.getTaskDefinitionKey())
                .execute();
        consumer.accept(task);
    }

    public List<HistoricDetail> history(String instanceId) {
        return historyService.createHistoricDetailQuery().processInstanceId(instanceId).list();
    }
}
