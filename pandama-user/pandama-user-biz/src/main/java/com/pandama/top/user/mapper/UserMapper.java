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
     * 页面
     *
     * @param page 页面
     * @param dto  入参
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.pandama.top.user.pojo.vo.UserSearchResultVO>
     * @author 王强
     */
    Page<UserSearchResultVO> page(Page<SysUser> page, @Param("dto") UserSearchDTO dto);

    /**
     * 获取用户列表通过部门id
     *
     * @param deptId 部门id
     * @return java.util.List<com.pandama.top.user.pojo.vo.UserDetailResultVO>
     * @author 王强
     */
    List<UserDetailResultVO> getUserListByDeptId(@Param("deptId") Long deptId);

    /**
     * 获取系统用户信息通过id
     *
     * @param userId 用户id
     * @return com.pandama.top.user.pojo.vo.SystemUserInfoVO
     * @author 王强
     */
    SystemUserInfoVO getSystemUserInfoById(@Param("userId") Long userId);

    /**
     * 获取用户信息通过id
     *
     * @param userId 用户id
     * @return com.pandama.top.user.pojo.vo.UserDetailResultVO
     * @author 王强
     */
    UserDetailResultVO getUserInfoById(@Param("userId") Long userId);

    /**
     * 获取登录信息通过用户名或电话
     *
     * @param username 账号
     * @param phone    手机号
     * @return com.pandama.top.user.api.pojo.vo.UserLoginVO
     * @author 王强
     */
    UserLoginVO getLoginInfoByUsernameOrPhone(@Param("username") String username, @Param("phone") String phone);
}
