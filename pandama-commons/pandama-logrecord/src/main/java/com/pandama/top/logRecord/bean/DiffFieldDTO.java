package com.pandama.top.logRecord.bean;

import lombok.Data;

/**
 * @description: 发生变化的字段信息dto
 * @author: 王强
 * @dateTime: 2022-09-01 17:57:19
 */
@Data
public class DiffFieldDTO {

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段别名
     */
    private String oldFieldAlias;

    /**
     * 字段别名
     */
    private String newFieldAlias;

    /**
     * 旧值
     */
    private Object oldValue;

    /**
     * 新值
     */
    private Object newValue;
}
