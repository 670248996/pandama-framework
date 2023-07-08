package com.pandama.top.user.entity;

import com.pandama.top.mybatisplus.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门信息表
 *
 * @author 王强
 * @date 2023-07-08 15:46:56
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class SysDept extends BaseEntity {
    /**
     * ids
     */
    private String ids;
    /**
     * 父级id
     */
    private Long parentId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 部门编号
     */
    private String deptCode;
    /**
     * 负责人名称
     */
    private String managerName;
    /**
     * 负责人手机号
     */
    private String managerPhone;
    /**
     * 负责人邮箱
     */
    private String managerEmail;
    /**
     * 是否启用
     */
    private Boolean status;
    /**
     * 部门等级
     */
    private Integer level;
    /**
     * 备注
     */
    private String remark;
}
