package com.pandama.top.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pandama.top.user.entity.SysRoleMenu;

import java.util.List;

/**
 * @description: 角色与菜单关联接口类
 * @author: 白剑民
 * @dateTime: 2022/10/17 17:37
 */
public interface RoleMenuService extends IService<SysRoleMenu> {

    /**
     * @param roleIds 角色id列表
     * @description: 获取指定角色列表下的关联菜单列表
     * @author: 白剑民
     * @date: 2022-10-31 15:30:49
     * @return: java.util.List<java.lang.Long>
     * @version: 1.0
     */
    List<Long> getMenuIdsByRoleIds(List<Long> roleIds);

    /**
     * @param menuIds 菜单id列表
     * @description: 获取指定菜单列表下的关联角色列表
     * @author: 白剑民
     * @date: 2022-10-31 17:53:34
     * @return: java.util.List<java.lang.Long>
     * @version: 1.0
     */
    List<Long> getRoleIdsByMenuIds(List<Long> menuIds);

    /**
     * @param roleIds 角色id列表
     * @description: 删除指定角色列表下的关联菜单列表
     * @author: 白剑民
     * @date: 2022-10-31 15:30:49
     * @return: void
     * @version: 1.0
     */
    void deleteByRoleIds(List<Long> roleIds);

    /**
     * @param menuIds 权限id列表
     * @description: 删除指定权限列表下的关联角色列表
     * @author: 白剑民
     * @date: 2022-10-31 17:53:34
     * @return: void
     * @version: 1.0
     */
    void deleteByMenuIds(List<Long> menuIds);
}
