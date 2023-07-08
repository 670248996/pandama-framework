package com.pandama.top.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pandama.top.user.entity.SysRoleMenu;

import java.util.List;

/**
 * 角色菜单服务
 *
 * @author 王强
 * @date 2023-07-08 15:56:17
 */
public interface RoleMenuService extends IService<SysRoleMenu> {

    /**
     * 获取菜单id通过角色id
     *
     * @param roleIds 角色id列表
     * @return java.util.List<java.lang.Long>
     * @author 王强
     */
    List<Long> getMenuIdsByRoleIds(List<Long> roleIds);

    /**
     * 获取角色id通过菜单id
     *
     * @param menuIds 菜单id列表
     * @return java.util.List<java.lang.Long>
     * @author 王强
     */
    List<Long> getRoleIdsByMenuIds(List<Long> menuIds);

    /**
     * 按角色删除id
     *
     * @param roleIds 角色id列表
     * @return void
     * @author 王强
     */
    void deleteByRoleIds(List<Long> roleIds);

    /**
     * 按菜单删除id
     *
     * @param menuIds 权限id列表
     * @return void
     * @author 王强
     */
    void deleteByMenuIds(List<Long> menuIds);
}
