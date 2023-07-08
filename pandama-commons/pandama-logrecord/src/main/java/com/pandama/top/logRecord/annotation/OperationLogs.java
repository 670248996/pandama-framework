package com.pandama.top.logRecord.annotation;

import java.lang.annotation.*;

/**
 * 操作日志
 *
 * @author 王强
 * @date 2023-07-08 15:20:39
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLogs {

    OperationLog[] value();
}
