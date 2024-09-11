package com.pandama.top.camunda.controller;

import camundajar.impl.com.google.gson.Gson;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.pandama.top.camunda.service.CamundaProcessService;
import com.pandama.top.core.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 流程控制器
 *
 * @author 王强
 * @date 2024-09-06 14:18:12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/process")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProcessController {
    private final CamundaProcessService processService;

    @GetMapping("/list")
    public Response<?> list() {
        List<ProcessDefinition> processDefinitionList = processService.getList();
        List<JSONObject> collect = processDefinitionList.stream()
                .map(p -> JSON.parseObject(new Gson().toJson(p))).collect(Collectors.toList());
        return Response.success(collect);
    }

    @PostMapping("/start/{definitionId}")
    public Response<?> start(@NotBlank(message = "流程定义ID不能为空") @PathVariable("definitionId") String definitionId,
                             @RequestBody Map<String, Object> variables) {
        ProcessInstance processInstance = processService.start(definitionId, null, variables);
        return Response.success("Process启动成功", JSON.parseObject(new Gson().toJson(processInstance)));
    }

    @PutMapping("/suspend/{definitionId}")
    public Response<?> suspend(@NotBlank(message = "流程定义ID不能为空") @PathVariable("definitionId") String definitionId) {
        processService.suspend(definitionId);
        return Response.success("暂停成功");
    }

    @PutMapping("/activate/{definitionId}")
    public Response<?> activate(@NotBlank(message = "流程定义ID不能为空") @PathVariable("definitionId") String definitionId) {
        processService.activate(definitionId);
        return Response.success("激活成功");
    }

    @DeleteMapping("/delete/{definitionId}")
    public Response<?> delete(@NotBlank(message = "流程定义ID不能为空") @PathVariable("definitionId") String definitionId) {
        processService.delete(definitionId);
        return Response.success("删除成功");
    }
}
