package com.pandama.top.mybatisplus.utils;

import com.pandama.top.mybatisplus.enums.DataPermEnum;
import org.springframework.core.NamedThreadLocal;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 获取当前数据权限信息
 *
 * @author 王强
 * @date 2023-11-13 13:30:51
 */
public class DataPermUtils {

    /**
     * 当前线程存储数据权限信息
     */
    private static final ThreadLocal<Map<DataPermEnum, List<Long>>> USER_LOCAL = new NamedThreadLocal<>("ThreadLocal DataPermUtils");

    /**
     * 获取数据权限信息
     *
     * @return java.util.Map<com.pandama.top.mybatisplus.enums.DataPermEnum, java.util.List < java.lang.Long>>
     * @author 王强
     */
    public static Map<DataPermEnum, List<Long>> getDataPerm() {
        return USER_LOCAL.get() == null ? new ConcurrentHashMap<>(16) : USER_LOCAL.get();
    }

    /**
     * 获取数据权限信息
     *
     * @param type 数据权限类型
     * @return java.util.List<java.lang.Long>
     * @author 王强
     */
    public static List<Long> getDataPerm(DataPermEnum type) {
        return getDataPerm().get(type);
    }

    /**
     * 设置数据权限
     *
     * @param map 数据权限信息Map
     * @return void
     * @author 王强
     */
    public static void setDataPerm(Map<DataPermEnum, List<Long>> map) {
        USER_LOCAL.set(map);
    }

    /**
     * 清除当前数据权限信息（防止内存泄露）
     *
     * @return void
     * @author 王强
     */
    public static void clearDataPerm() {
        USER_LOCAL.remove();
    }

}
