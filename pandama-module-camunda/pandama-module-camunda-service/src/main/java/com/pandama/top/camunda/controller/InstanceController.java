package com.pandama.top.camunda.controller;

import com.pandama.top.camunda.service.CamundaInstanceService;
import com.pandama.top.core.global.response.Response;
import com.pandama.top.core.pojo.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

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
        return Response.success(instanceService.list());
    }

    @PostMapping("/page")
    public Response<?> list(@RequestBody PageDTO dto) {
        return Response.success(instanceService.page(dto));
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
