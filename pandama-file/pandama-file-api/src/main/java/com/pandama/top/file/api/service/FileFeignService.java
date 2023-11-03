package com.pandama.top.file.api.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 文件Feign服务
 *
 * @author 王强
 * @date 2023-07-08 15:37:02
 */
@FeignClient(name = "pandama-file", path = "/pandama/file")
public interface FileFeignService {

}
