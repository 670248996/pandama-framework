package com.pandama.top.camunda.open.facade;

import com.pandama.top.camunda.open.interfaces.ProcessVariable;
import com.pandama.top.core.global.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 工作流Feign服务
 *
 * @author 王强
 * @date 2023-07-08 15:40:04
 */
@FeignClient(contextId = "TaskFeignService", name = "pandama-camunda", path = "/pandama/camunda/task")
public interface TaskFeignService {

    @PostMapping("/start/{definitionId}")
    <T extends ProcessVariable> Response<?> start(@PathVariable("definitionId") String definitionId, @RequestBody T dto);
}
