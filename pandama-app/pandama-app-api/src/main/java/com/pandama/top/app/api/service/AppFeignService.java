package com.pandama.top.app.api.service;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "pandama-user", path = "/pandama/app")
public interface AppFeignService {

}
