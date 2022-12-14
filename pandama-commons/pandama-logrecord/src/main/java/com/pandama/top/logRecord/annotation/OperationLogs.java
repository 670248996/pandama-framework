package com.pandama.top.logRecord.annotation;

import java.lang.annotation.*;

/**
 * @description: 操作日志
 * @author: 王强
 * @dateTime: 2022-09-02 16:29:09
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLogs {

    OperationLog[] value();
}
