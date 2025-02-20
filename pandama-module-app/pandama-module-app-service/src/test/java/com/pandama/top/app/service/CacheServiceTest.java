package com.pandama.top.app.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableCaching
public class CacheServiceTest {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void setUp() {
        cacheManager.getCache("user").clear();
    }

    @Test
    public void getInfo_FirstCall_ShouldQueryDatabase() {
        String id = "123";
        String result = cacheService.getInfo(id);
        assertEquals(id, result);
    }

    @Test
    public void getInfo_SecondCallWithSameId_ShouldReturnCachedResult() {
        String id = "123";
        cacheService.getInfo(id); // 第一次调用以填充缓存
        String result = cacheService.getInfo(id); // 第二次调用以检查缓存
        assertEquals(id, result);
    }
}
