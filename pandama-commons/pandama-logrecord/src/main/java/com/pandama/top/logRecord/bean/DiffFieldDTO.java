package com.pandama.top.logRecord.bean;

import lombok.Data;

/**
 * 发生变化的字段信息dto
 *
 * @author 王强
 * @date 2023-07-08 15:21:16
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
