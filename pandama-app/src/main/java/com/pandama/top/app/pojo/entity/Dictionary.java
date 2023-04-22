package com.pandama.top.app.pojo.entity;

import com.pandama.top.mybatisplus.entity.BaseEntity;
import lombok.Data;

/**
 * @description: 字典
 * @author: 王强
 * @dateTime: 2023-04-22 12:27:36
 */
@Data
public class Dictionary extends BaseEntity {
    /**
     * 上级字典id
     */
    private Long parentId;
    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 字典代码
     */
    private String dictCode;
    /**
     * 字典名称
     */
    private String dictName;
    /**
     * 是否启用（默认1，启用）
     */
    private Boolean isEnable;
    /**
     * 字典等级
     */
    private Integer level;
    /**
     * 备注
     */
    private String remark;
}
