package com.pandama.top.app.controller;

import com.alibaba.fastjson2.JSONObject;
import com.pandama.top.app.service.CacheService;
import com.pandama.top.core.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

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

    private final CacheService cacheService;

    private final RedisTemplate redisTemplate;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Response<?> get(String id) {
        return Response.success(cacheService.getInfo(id));
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Response<?> info() {
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info());
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) connection -> connection.dbSize());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("info", info);
        jsonObject.put("commandStats", commandStats);
        jsonObject.put("dbSize", dbSize);
        return Response.success(jsonObject);
    }
}
