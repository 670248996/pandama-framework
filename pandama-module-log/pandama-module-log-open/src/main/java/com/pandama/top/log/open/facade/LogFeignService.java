package com.pandama.top.log.open.facade;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 日志Feign服务
 *
 * @author 王强
 * @date 2023-07-08 15:37:02
 */
@FeignClient(name = "pandama-log", path = "/pandama/log")
public interface LogFeignService {

}
