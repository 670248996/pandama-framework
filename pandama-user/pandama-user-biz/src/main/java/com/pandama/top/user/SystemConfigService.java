package com.pandama.top.user;

import com.pandama.top.user.entity.SysDict;
import com.pandama.top.user.mapper.DictMapper;
import com.pandama.top.user.pojo.dto.DictSearchDTO;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 系统配置服务
 * @author: 王强
 * @dateTime: 2023-06-17 13:35:12
 */
@Slf4j
public class SystemConfigService {

    private final DictMapper dictMapper;

    /**
     * 存放一些系统配置的缓存 map
     */
    private static final Map<String, String> SYS_CONF_CACHE = new HashMap<>();

    public SystemConfigService(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    /**
     * @description: Bean 的初始化方法
     * @author: 王强
     * @date: 2023-06-17 14:11:29
     * @return: void
     * @version: 1.0
     */
    @PostConstruct
    public void init() {
        List<SysDict> list = dictMapper.list(new DictSearchDTO());
        log.info("访问 DB，捞取数据放入缓存的 map 中");
    }

    public static String getSystemConfig(String key) {
        return SYS_CONF_CACHE.get(key);
    }
}
