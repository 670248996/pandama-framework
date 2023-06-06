package com.pandama.top.user.biz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 部门与用户关联表
 * @author: 白剑民
 * @dateTime: 2022/10/17 16:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDeptUser {
    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 用户id
     */
    private Long userId;
}
