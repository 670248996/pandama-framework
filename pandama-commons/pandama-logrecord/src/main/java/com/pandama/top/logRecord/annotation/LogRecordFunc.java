package com.pandama.top.logRecord.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志记录函数
 *
 * @author 王强
 * @date 2023-07-08 15:20:35
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface LogRecordFunc {

    /**
     * 自定义函数的别名，如果为空即使用函数名
     */
    String value() default "";
}
