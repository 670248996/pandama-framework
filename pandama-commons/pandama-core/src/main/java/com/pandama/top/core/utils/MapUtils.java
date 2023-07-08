package com.pandama.top.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Map工具
 *
 * @author 王强
 * @date 2023-07-08 15:15:24
 */
public class MapUtils {

    /**
     * Bean转Map
     *
     * @param object 对象
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author 王强
     */
    public static Map<String, Object> beanToMap(Object object) {
        Map<String, Object> map = new HashMap<>(16);
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(object) != null) {
                    map.put(field.getName(), field.get(object));
                }
            }
        } catch (Exception ignored) {}
        return map;
    }

    /**
     * Map转Bean
     *
     * @param map       地图
     * @param beanClass Bean类
     * @return T
     * @author 王强
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass) throws Exception {
        T object = beanClass.newInstance();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            if (map.containsKey(field.getName())) {
                field.set(object, map.get(field.getName()));
            }
        }
        return object;
    }
}
