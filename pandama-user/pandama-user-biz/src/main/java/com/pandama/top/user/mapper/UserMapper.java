package com.pandama.top.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandama.top.user.api.pojo.vo.UserLoginVO;
import com.pandama.top.user.entity.SysUser;
import com.pandama.top.user.pojo.dto.UserSearchDTO;
import com.pandama.top.user.pojo.vo.SystemUserInfoVO;
import com.pandama.top.user.pojo.vo.UserDetailResultVO;
import com.pandama.top.user.pojo.vo.UserSearchResultVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 用户信息Mapper接口
 * @author: 白剑民
 * @dateTime: 2022/10/17 17:36
 */
@Repository
public interface UserMapper extends BaseMapper<SysUser> {

    /**
     * @param page 页面
     * @param dto  入参
     * @description: 页面
     * @author: 王强
     * @date: 2023-05-24 13:35:51
     * @return: Page<SysUser>
     * @version: 1.0
     */
    Page<UserSearchResultVO> page(Page<SysUser> page, @Param("dto") UserSearchDTO dto);

    /**
     * @param enterpriseId 企业/机构id
     * @description: 根据企业/机构id获取其下所有用户列表
     * @author: 白剑民
     * @date: 2022-10-21 17:22:35
     * @return: java.util.List<com.gientech.iot.user.entity.vo.UserInfoVO>
     * @version: 1.0
     */
    List<UserDetailResultVO> getUserListByEnterpriseId(@Param("enterpriseId") Long enterpriseId);

    /**
     * @param deptId 部门id
     * @description: 根据部门id获取其下所有用户列表
     * @author: 白剑民
     * @date: 2022-10-24 16:56:51
     * @return: java.util.List<com.gientech.iot.user.entity.vo.UserInfoVO>
     * @version: 1.0
     */
    List<UserDetailResultVO> getUserListByDeptId(@Param("deptId") Long deptId);

    /**
     * @param userId 用户id
     * @description: 根据id获取用户信息
     * @author: 白剑民
     * @date: 2022-10-26 17:08:29
     * @return: com.gientech.iot.user.entity.vo.UserInfoVO
     * @version: 1.0
     */
    SystemUserInfoVO getSystemUserInfoById(@Param("userId") Long userId);

    /**
     * @param userId 用户id
     * @description: 根据id获取用户信息
     * @author: 白剑民
     * @date: 2022-10-26 17:08:29
     * @return: com.gientech.iot.user.entity.vo.UserInfoVO
     * @version: 1.0
     */
    UserDetailResultVO getUserInfoById(@Param("userId") Long userId);

    /**
     * @param username 账号
     * @param phone    手机号
     * @description: 根据账号或手机号获取用户及密码策略信息
     * @author: 白剑民
     * @date: 2023-04-06 13:43:24
     * @return: com.gientech.iot.user.api.pojo.vo.LoginVO
     * @version: 1.0
     */
    UserLoginVO getLoginInfoByUsernameOrPhone(@Param("username") String username, @Param("phone") String phone);
}
