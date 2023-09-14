package com.pandama.top.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部门与用户关联表
 *
 * @author 王强
 * @date 2023-07-08 15:46:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserDept {
    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 用户id
     */
    private Long userId;
}
