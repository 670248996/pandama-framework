package com.pandama.top.camunda.controller;

import camundajar.impl.com.google.gson.Gson;
import com.alibaba.fastjson2.JSON;
import com.pandama.top.camunda.service.CamundaProcessService;
import com.pandama.top.core.global.response.Response;
import com.pandama.top.core.pojo.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;

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
        return Response.success(processService.list());
    }

    @PostMapping("/page")
    public Response<?> page(@RequestBody PageDTO dto) {
        return Response.success(processService.page(dto));
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
