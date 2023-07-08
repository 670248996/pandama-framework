package com.pandama.top.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户与角色关联表
 *
 * @author 王强
 * @date 2023-07-08 15:47:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRole {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 角色id
     */
    private Long roleId;
}
