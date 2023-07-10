package com.pandama.top.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 广播模式监听频道
 *
 * @author 王强
 * @date 2023-07-10 13:47:56
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface RedisListener {

    /**
     * 名称
     *
     * @return java.lang.String
     * @author 王强
     */
    String value();
}
