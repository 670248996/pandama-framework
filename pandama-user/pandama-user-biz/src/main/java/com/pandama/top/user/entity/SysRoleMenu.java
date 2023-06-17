package com.pandama.top.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 角色与菜单关联表
 * @author: 白剑民
 * @dateTime: 2022/10/17 16:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleMenu {
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 菜单id
     */
    private Long menuId;
}
