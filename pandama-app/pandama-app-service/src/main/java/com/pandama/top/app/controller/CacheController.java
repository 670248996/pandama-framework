package com.pandama.top.app.controller;

import com.pandama.top.app.service.CacheService;
import com.pandama.top.core.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 缓存控制器
 *
 * @author 王强
 * @date 2023-07-08 11:55:46
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/cache")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Response<?> get(String id) {
        return Response.success(cacheService.getInfo(id));
    }
}
