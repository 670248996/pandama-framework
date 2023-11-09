package com.pandama.top.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pandama.top.user.entity.SysUserDept;

import java.util.List;

/**
 * 部门用户服务
 *
 * @author 王强
 * @date 2023-07-08 15:54:52
 */
public interface DeptUserService extends IService<SysUserDept> {

    /**
     * 获取用户id通过部门id
     *
     * @param deptIds 部门id列表
     * @return java.util.List<java.lang.Long>
     * @author 王强
     */
    List<Long> getUserIdsByDeptIds(List<Long> deptIds);

    /**
     * 获取部门id通过用户id
     *
     * @param userIds 用户id列表
     * @return java.util.List<java.lang.Long>
     * @author 王强
     */
    List<Long> getDeptIdsByUserIds(List<Long> userIds);

    /**
     * 获取列表通过部门id
     *
     * @param deptIds 部门id列表
     * @return java.util.List<com.pandama.top.user.entity.SysDeptUser>
     * @author 王强
     */
    List<SysUserDept> getListByDeptIds(List<Long> deptIds);

    /**
     * 获取列表通过用户id
     *
     * @param userIds 用户id列表
     * @return java.util.List<com.pandama.top.user.entity.SysDeptUser>
     * @author 王强
     */
    List<SysUserDept> getListByUserIds(List<Long> userIds);

    /**
     * 按部门id删除
     *
     * @param deptIds 部门id列表
     * @author 王强
     */
    void deleteByDeptIds(List<Long> deptIds);

    /**
     * 按用户id删除
     *
     * @param userIds 用户id列表
     * @author 王强
     */
    void deleteByUserIds(List<Long> userIds);
}
