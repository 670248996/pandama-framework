package com.pandama.top.user.entity;

import com.pandama.top.mybatisplus.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @description: 字典信息表
 * @author: 白剑民
 * @dateTime: 2022/10/17 17:26
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class SysDict extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -4092659259326924165L;
    /**
     * ids
     */
    private String ids;
    /**
     * 上级字典id
     */
    private Long parentId;
    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 字典代码（每三个数字为一级）
     */
    private String dictCode;
    /**
     * 字典名称
     */
    private String dictName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否启用
     */
    private Boolean status;
    /**
     * 字典等级
     */
    private Integer level;
}
