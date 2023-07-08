package com.pandama.top.user.entity;

import com.pandama.top.mybatisplus.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色信息表
 *
 * @author 王强
 * @date 2023-07-08 15:47:20
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class SysRole extends BaseEntity {
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色编号
     */
    private String roleCode;
    /**
     * 角色等级
     */
    private Integer level;
    /**
     * 是否可用(0: 不可用, 1: 可用)
     */
    private Boolean status;
    /**
     * 备注
     */
    private String remark;
}
