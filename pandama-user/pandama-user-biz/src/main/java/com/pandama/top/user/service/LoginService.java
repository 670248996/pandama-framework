package com.pandama.top.user.service;

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
     * @param username 入参
     * @return com.pandama.top.user.api.pojo.vo.UserLoginVO
     * @author 王强
     */
    UserLoginVO loadUserByUsername(String username);

    /**
     * 登录通过电话
     *
     * @param phone 入参
     * @return com.pandama.top.user.api.pojo.vo.UserLoginVO
     * @author 王强
     */
    UserLoginVO loadUserByPhone(String phone);
}
