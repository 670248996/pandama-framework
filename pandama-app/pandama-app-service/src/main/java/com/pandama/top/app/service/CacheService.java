package com.pandama.top.app.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 缓存服务
 *
 * @author 王强
 * @date 2024-08-24 21:19:33
 */
@Service
public class CacheService {

    @Cacheable(value = "user")
    public String getInfo(String id) {
        System.out.println("查询数据库");
        return id;
    }
}
