package com.pandama.top.camunda.open.facade;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 工作流Feign服务
 *
 * @author 王强
 * @date 2023-07-08 15:40:04
 */
@FeignClient(contextId = "ProcessFeignService", name = "pandama-camunda", path = "/pandama/camunda/process")
public interface ProcessFeignService {

}
