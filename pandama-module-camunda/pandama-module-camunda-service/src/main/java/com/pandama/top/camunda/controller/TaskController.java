package com.pandama.top.camunda.controller;

import camundajar.impl.com.google.gson.Gson;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.pandama.top.camunda.service.CamundaTaskService;
import com.pandama.top.core.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.history.HistoricDetail;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 任务控制器
 *
 * @author 王强
 * @date 2024-09-06 14:18:12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TaskController {
    private final CamundaTaskService taskService;

    @GetMapping("/list/{assignee}")
    public Response<?> list(@NotBlank(message = "接收人不能为空") @PathVariable("assignee") String assignee) {
        List<Task> taskList = taskService.list(assignee);
        List<JSONObject> collect = taskList.stream()
                .map(p -> JSON.parseObject(new Gson().toJson(p))).collect(Collectors.toList());
        return Response.success(collect);
    }

    @PostMapping("/start/{definitionId}")
    public Response<?> start(@NotBlank(message = "流程定义ID不能为空") @PathVariable("definitionId") String definitionId,
                             @RequestBody Map<String, Object> variables) {
        Task taskInfo = taskService.start(definitionId, variables, task -> log.info("流程启动成功: {}", task.getName()));
        return Response.success(JSON.parseObject(new Gson().toJson(taskInfo)));
    }

    @PostMapping("/approve/{taskId}")
    public Response<?> approve(@NotBlank(message = "任务实例ID不能为空") @PathVariable("taskId") String taskId,
                               @RequestBody Map<String, Object> variables) {
        taskService.approve(taskId, variables, task -> log.info("流程审批通过: {}", task.getName()));
        return Response.success();
    }

    @PostMapping("/reject/{taskId}")
    public Response<?> reject(@NotBlank(message = "任务实例ID不能为空") @PathVariable("taskId") String taskId,
                              @RequestBody Map<String, Object> variables) {
        taskService.reject(taskId, variables, task -> log.info("流程审批拒绝: {}", task.getName()));
        return Response.success();
    }

    @PostMapping("/reback/{taskId}")
    public Response<?> reback(@NotBlank(message = "任务实例ID不能为空") @PathVariable("taskId") String taskId) {
        taskService.reback(taskId, task -> log.info("流程回退成功: {}", task.getName()));
        return Response.success();
    }

    @GetMapping("/history/{instanceId}")
    public Response<?> history(@NotBlank(message = "流程实例ID不能为空") @PathVariable("instanceId") String instanceId) {
        List<HistoricDetail> taskList = taskService.history(instanceId);
        return Response.success(taskList);
    }
}
