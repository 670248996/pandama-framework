package com.pandama.top.camunda.controller;

import camundajar.impl.com.google.gson.Gson;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.pandama.top.camunda.service.CamundaInstanceService;
import com.pandama.top.core.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实例控制器
 *
 * @author 王强
 * @date 2024-09-06 14:18:12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/instance")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class InstanceController {
    private final CamundaInstanceService instanceService;

    @GetMapping("/list")
    public Response<?> list() {
        List<ProcessInstance> processInstanceList = instanceService.getList();
        List<JSONObject> collect = processInstanceList.stream()
                .map(p -> JSON.parseObject(new Gson().toJson(p))).collect(Collectors.toList());
        return Response.success(collect);
    }

    @PutMapping("/suspend/{instanceId}")
    public Response<?> suspend(@NotBlank(message = "流程实例ID不能为空") @PathVariable("instanceId") String instanceId) {
        instanceService.suspend(instanceId);
        return Response.success("暂停成功");
    }

    @PutMapping("/activate/{instanceId}")
    public Response<?> activate(@NotBlank(message = "流程实例ID不能为空") @PathVariable("instanceId") String instanceId) {
        instanceService.activate(instanceId);
        return Response.success("激活成功");
    }

    @DeleteMapping("/delete/{instanceId}")
    public Response<?> delete(@NotBlank(message = "流程实例ID不能为空") @PathVariable("instanceId") String definitionId) {
        instanceService.delete(definitionId);
        return Response.success("删除成功");
    }
}
