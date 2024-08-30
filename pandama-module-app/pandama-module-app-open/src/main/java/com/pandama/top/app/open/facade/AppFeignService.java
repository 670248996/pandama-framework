package com.pandama.top.app.open.facade;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * AppFeign服务
 *
 * @author 王强
 * @date 2023-07-08 11:54:46
 */
@FeignClient(name = "pandama-user", path = "/pandama/app")
public interface AppFeignService {

}
