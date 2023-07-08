package com.pandama.top.logRecord.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志记录差异
 *
 * @author 王强
 * @date 2023-07-08 15:20:31
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogRecordDiff {

    /**
     * 类/字段的别名 不填则默认类/字段名
     */
    String alias() default "";
}
