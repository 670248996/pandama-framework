package com.pandama.top.camunda.service;

import camundajar.impl.com.google.gson.Gson;
import camundajar.impl.com.google.gson.GsonBuilder;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.pandama.top.core.pojo.dto.PageDTO;
import com.pandama.top.core.pojo.vo.PageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricDetail;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CamundaTaskService {
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private final org.camunda.bpm.engine.TaskService taskService;
    private final HistoryService historyService;
    private final RuntimeService runtimeService;

    public List<JSONObject> list(String assignee) {
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(assignee).orderByDueDate().desc().list();
        return taskList.stream().map(p -> JSON.parseObject(gson.toJson(p))).collect(Collectors.toList());
    }

    public PageVO<JSONObject> page(String assignee, PageDTO dto) {
        long count = taskService.createTaskQuery().taskAssignee(assignee).count();
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(assignee)
                .orderByDueDate().desc().listPage(dto.getFirstSize(), dto.getLastSize());
        List<JSONObject> collect = taskList.stream()
                .map(p -> JSON.parseObject(gson.toJson(p))).collect(Collectors.toList());
        return new PageVO<>(count, dto.getSize().longValue(), dto.getCurrent().longValue(), collect);
    }

    @Transactional(rollbackFor = Exception.class)
    public JSONObject start(String definitionId, Map<String, Object> variables, Consumer<Task> consumer) {
        ProcessInstance instance = runtimeService.startProcessInstanceById(definitionId, null, variables);
        Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
        taskService.setVariables(task.getId(), variables);
        taskService.complete(task.getId());
        long count = runtimeService.createExecutionQuery().processInstanceId(task.getProcessInstanceId()).count();
        if (count == 0) {
            consumer.accept(task);
        }
        return JSON.parseObject(gson.toJson(task));
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
