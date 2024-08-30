package com.pandama.top.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pandama.top.user.entity.SysUserRole;

import java.util.List;

/**
 * 用户角色服务
 *
 * @author 王强
 * @date 2023-07-08 15:57:07
 */
public interface UserRoleService extends IService<SysUserRole> {

    /**
     * 获取用户id通过角色id
     *
     * @param roleIds 角色id列表
     * @return java.util.List<java.lang.Long>
     * @author 王强
     */
    List<Long> getUserIdsByRoleIds(List<Long> roleIds);

    /**
     * 获取角色id通过用户id
     *
     * @param userIds 用户id列表
     * @return java.util.List<java.lang.Long>
     * @author 王强
     */
    List<Long> getRoleIdsByUserIds(List<Long> userIds);
}
