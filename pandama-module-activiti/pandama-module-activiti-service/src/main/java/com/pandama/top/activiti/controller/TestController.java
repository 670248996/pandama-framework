package com.pandama.top.activiti.controller;

import com.pandama.top.core.global.response.Response;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class TestController {
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;

    /**
     * 通过流程定义key，发起一个流程实例
     * @param processKey 流程定义key
     * @return 流程实例ID
     */
    @GetMapping(value = "/startProcessInstanceByKey/{processKey}")
    public String startProcessInstanceByKey(@PathVariable("processKey") String processKey) {
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(processKey);
        return instance.getRootProcessInstanceId();
    }

    /**
     * 查询某个用户的待办任务
     * @param assignee 用户ID
     * @return 待办任务列表
     */
    @GetMapping(value = "/getTaskByAssignee/{assignee}")
    public Response<?> getTaskByAssignee(@PathVariable("assignee") String assignee) {
        List<Task> list = taskService.createTaskQuery().taskAssignee(assignee).list();
        List<String> collect = list.stream().map(Task::getId).collect(Collectors.toList());
        return Response.success(collect);
    }
}
