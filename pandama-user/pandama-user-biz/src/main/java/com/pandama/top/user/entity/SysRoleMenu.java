package com.pandama.top.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色与菜单关联表
 *
 * @author 王强
 * @date 2023-07-08 15:47:25
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
