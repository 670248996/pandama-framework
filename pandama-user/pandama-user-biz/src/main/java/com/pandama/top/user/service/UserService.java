package com.pandama.top.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pandama.top.core.pojo.vo.PageVO;
import com.pandama.top.user.entity.SysUser;
import com.pandama.top.user.pojo.dto.*;
import com.pandama.top.user.pojo.vo.UserAuthRoleVO;
import com.pandama.top.user.pojo.vo.UserDetailResultVO;
import com.pandama.top.user.pojo.vo.UserSearchResultVO;

import java.util.List;

/**
 * 用户服务
 *
 * @author 王强
 * @date 2023-07-08 15:57:12
 */
public interface UserService extends IService<SysUser> {

    /**
     * 注册
     *
     * @param dto 用户注册传参
     * @author 王强
     */
    void register(UserRegisterDTO dto);

    /**
     * 批量注册
     *
     * @param dtoList 批量用户注册传参
     * @author 王强
     */
    void batchRegister(List<UserRegisterDTO> dtoList);

    /**
     * 删除
     *
     * @param ids 用户id列表
     * @author 王强
     */
    void delete(List<Long> ids);

    /**
     * 更新
     *
     * @param dto 用户信息更新传参
     * @author 王强
     */
    void update(UserUpdateDTO dto);

    /**
     * 页面
     *
     * @param dto 获取用户列表入参
     * @return com.pandama.top.core.pojo.vo.PageVO<com.pandama.top.user.pojo.vo.UserSearchResultVO>
     * @author 王强
     */
    PageVO<UserSearchResultVO> page(UserSearchDTO dto);

    /**
     * 获取指定部门下所有用户列表
     *
     * @param deptId 部门id
     * @return java.util.List<com.pandama.top.user.pojo.vo.UserDetailResultVO>
     * @author 王强
     */
    List<UserDetailResultVO> listByDeptId(Long deptId);

    /**
     * 获取用户信息通过id
     *
     * @param id 用户id
     * @return com.pandama.top.user.pojo.vo.UserDetailResultVO
     * @author 王强
     */
    UserDetailResultVO getUserInfoById(Long id);

    /**
     * 改变状态
     *
     * @param id 用户id
     * @param status 启用或禁用
     * @author 王强
     */
    void changeStatus(Long id, Boolean status);

    /**
     * 更新密码
     *
     * @param dto 用户密码修改传参
     * @author 王强
     */
    void updatePassword(PasswordUpdateDTO dto);

    /**
     * 用户查询授权角色分页
     *
     * @param dto 入参
     * @return com.pandama.top.core.pojo.vo.PageVO<com.pandama.top.user.pojo.vo.UserAuthRoleVO>
     * @author 王强
     */
    PageVO<UserAuthRoleVO> authRolePage(UserAuthRoleSearchDTO dto);

    /**
     * 角色授权用户信息列表
     *
     * @param dto 入参
     * @return com.pandama.top.core.pojo.vo.PageVO<com.pandama.top.user.pojo.vo.UserAuthRoleVO>
     * @author 王强
     */
    PageVO<UserAuthRoleVO> unAuthRolePage(UserAuthRoleSearchDTO dto);

    /**
     * 角色授权用户信息列表
     *
     * @param dto 入参
     * @author 王强
     */
    void authRoleCancel(UserAuthRoleCancelDTO dto);

    /**
     * 角色授权用户
     *
     * @param dto 入参
     * @author 王强
     */
    void authRoleConfirm(UserAuthRoleConfirmDTO dto);
}
