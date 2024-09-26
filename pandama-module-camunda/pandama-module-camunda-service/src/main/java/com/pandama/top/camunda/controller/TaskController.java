package com.pandama.top.camunda.controller;

import com.pandama.top.camunda.camunda.CamundaTaskService;
import com.pandama.top.core.global.response.Response;
import com.pandama.top.core.pojo.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.history.HistoricDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

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
    public Response<?> page(@NotBlank(message = "接收人不能为空") @PathVariable("assignee") String assignee) {
        return Response.success(taskService.list(assignee));
    }

    @PostMapping("/page/{assignee}")
    public Response<?> page(@NotBlank(message = "接收人不能为空") @PathVariable("assignee") String assignee, @RequestBody PageDTO dto) {
        return Response.success(taskService.page(assignee, dto));
    }

    @PostMapping("/start/{definitionId}")
    public Response<?> start(@NotBlank(message = "流程定义ID不能为空") @PathVariable("definitionId") String definitionId,
                             @RequestBody Map<String, Object> variables) {
        taskService.start(definitionId, variables, task -> log.info("流程启动成功: {}", task.getName()));
        return Response.success();
    }

    @PostMapping("/approve/{taskId}")
    public Response<?> approve(@NotBlank(message = "任务实例ID不能为空") @PathVariable("taskId") String taskId,
                               @RequestBody Map<String, Object> variables) {
        taskService.approve(taskId, variables, task -> log.info("任务审批通过: {}", task.getName()));
        return Response.success();
    }

    @PostMapping("/reject/{taskId}")
    public Response<?> reject(@NotBlank(message = "任务实例ID不能为空") @PathVariable("taskId") String taskId,
                              @RequestBody Map<String, Object> variables) {
        taskService.reject(taskId, variables, task -> log.info("任务审批拒绝: {}", task.getName()));
        return Response.success();
    }

    @PostMapping("/reback/{taskId}")
    public Response<?> reback(@NotBlank(message = "任务实例ID不能为空") @PathVariable("taskId") String taskId) {
        taskService.reback(taskId, task -> log.info("任务回退成功: {}", task.getName()));
        return Response.success();
    }

    @GetMapping("/history/{instanceId}")
    public Response<?> history(@NotBlank(message = "流程实例ID不能为空") @PathVariable("instanceId") String instanceId) {
        List<HistoricDetail> taskList = taskService.history(instanceId);
        return Response.success(taskList);
    }
}
