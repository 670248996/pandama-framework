package com.pandama.top.user.service;

import com.pandama.top.user.pojo.vo.SystemUserInfoVO;
import com.pandama.top.user.api.pojo.vo.UserLoginVO;

/**
 * 登录服务
 *
 * @author 王强
 * @date 2023-07-08 15:55:26
 */
public interface LoginService {

    /**
     * 登录用户名
     *
     * @param dto 入参
     * @return com.pandama.top.user.api.pojo.vo.UserLoginVO
     * @author 王强
     */
    UserLoginVO loginByUsername(String dto);

    /**
     * 登录通过电话
     *
     * @param dto 入参
     * @return com.pandama.top.user.api.pojo.vo.UserLoginVO
     * @author 王强
     */
    UserLoginVO loginByPhone(String dto);

    /**
     * 获取用户信息
     *
     * @return com.pandama.top.user.pojo.vo.SystemUserInfoVO
     * @author 王强
     */
    SystemUserInfoVO getUserInfo();
}
