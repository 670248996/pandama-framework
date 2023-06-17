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
 * @description: 用户信息接口类
 * @author: 白剑民
 * @dateTime: 2022/10/17 17:37
 */
public interface UserService extends IService<SysUser> {

    /**
     * @param dto 用户注册传参
     * @description: 用户注册
     * @author: 白剑民
     * @date: 2022-10-28 16:21:30
     * @return: com.gientech.iot.user.entity.vo.UserRegisterResultVO
     * @version: 1.0
     */
    void register(UserRegisterDTO dto);

    /**
     * @param dtoList 批量用户注册传参
     * @description: 批量用户注册
     * @author: 白剑民
     * @date: 2022-10-31 10:51:39
     * @return: java.util.List<com.gientech.iot.user.entity.vo.UserRegisterResultVO>
     * @version: 1.0
     */
    void batchRegister(List<UserRegisterDTO> dtoList);

    /**
     * @param userIds 用户id列表
     * @description: 删除用户信息
     * @author: 白剑民
     * @date: 2022-10-27 10:55:34
     * @version: 1.0
     */
    void delete(List<Long> userIds);

    /**
     * @param dto 用户信息更新传参
     * @description: 用户信息更新
     * @author: 白剑民
     * @date: 2022-10-27 09:36:41
     * @version: 1.0
     */
    void update(UserUpdateDTO dto);

    /**
     * @param dto 获取用户列表入参
     * @description: 页面
     * @author: 王强
     * @date: 2023-05-24 10:01:07
     * @return: PageResultVO<UserInfoVO>
     * @version: 1.0
     */
    PageVO<UserSearchResultVO> page(UserSearchDTO dto);

    /**
     * @param enterpriseId 企业/机构id
     * @description: 获取企业/机构其下所有用户列表
     * @author: 白剑民
     * @date: 2022-10-21 17:21:05
     * @return: java.util.List<com.gientech.iot.user.entity.vo.UserInfoVO>
     * @version: 1.0
     */
    List<UserDetailResultVO> listByEnterpriseId(Long enterpriseId);

    /**
     * @param deptId 部门id
     * @description: 获取指定部门下所有用户列表
     * @author: 白剑民
     * @date: 2022-10-24 16:55:46
     * @return: java.util.List<com.gientech.iot.user.entity.vo.UserInfoVO>
     * @version: 1.0
     */
    List<UserDetailResultVO> listByDeptId(Long deptId);

    /**
     * @param userId 用户id
     * @description: 根据id获取用户信息
     * @author: 白剑民
     * @date: 2022-10-26 17:07:13
     * @return: com.gientech.iot.user.entity.vo.UserInfoVO
     * @version: 1.0
     */
    UserDetailResultVO getUserInfoById(Long userId);

    /**
     * @param userId   用户id
     * @param status 启用或禁用
     * @description: 启用或禁用用户账号
     * @author: 白剑民
     * @date: 2022-10-27 10:02:51
     * @version: 1.0
     */
    void changeStatus(Long userId, Boolean status);

    /**
     * @param dto 用户密码修改传参
     * @description: 用户密码修改
     * @author: 白剑民
     * @date: 2022-10-27 10:33:07
     * @version: 1.0
     */
    void updatePassword(PasswordUpdateDTO dto);

    /**
     * @param dto 入参
     * @description: 用户查询授权角色分页
     * @author: 王强
     * @date: 2023-05-24 15:54:47
     * @return: PageResultVO<?>
     * @version: 1.0
     */
    PageVO<UserAuthRoleVO> authRolePage(UserAuthRoleSearchDTO dto);

    /**
     * @param dto 入参
     * @description: 角色授权用户信息列表
     * @author: 王强
     * @date: 2023-05-23 12:18:48
     * @return: PageResultVO<RoleAuthUserVO>
     * @version: 1.0
     */
    PageVO<UserAuthRoleVO> unAuthRolePage(UserAuthRoleSearchDTO dto);

    /**
     * @param dto 入参
     * @description: 角色授权用户信息列表
     * @author: 王强
     * @date: 2023-05-23 12:18:48
     * @return: PageResultVO<RoleAuthUserVO>
     * @version: 1.0
     */
    void authRoleCancel(UserAuthRoleCancelDTO dto);

    /**
     * @param dto 入参
     * @description: 角色授权用户
     * @author: 王强
     * @date: 2023-05-23 12:43:44
     * @return: void
     * @version: 1.0
     */
    void authRoleConfirm(UserAuthRoleConfirmDTO dto);
}
