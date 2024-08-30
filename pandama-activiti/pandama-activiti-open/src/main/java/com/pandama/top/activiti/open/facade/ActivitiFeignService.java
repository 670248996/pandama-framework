package com.pandama.top.activiti.open.facade;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 工作流Feign服务
 *
 * @author 王强
 * @date 2023-07-08 15:40:04
 */
@FeignClient(name = "pandama-activiti", path = "/pandama/activiti")
public interface ActivitiFeignService {

}
