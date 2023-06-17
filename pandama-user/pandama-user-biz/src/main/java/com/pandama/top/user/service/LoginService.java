package com.pandama.top.user.service;

import com.pandama.top.user.pojo.vo.SystemUserInfoVO;
import com.pandama.top.user.api.pojo.vo.UserLoginVO;

/**
 * @description: 用户服务
 * @author: 王强
 * @dateTime: 2023-04-20 14:45:24
 */
public interface LoginService {

    /**
     * @param dto 入参
     * @description: 根据用户名登录
     * @author: 王强
     * @date: 2023-06-16 15:48:53
     * @return: UserLoginVO
     * @version: 1.0
     */
    UserLoginVO loginByUsername(String dto);

    /**
     * @param dto 入参
     * @description: 功能手机号登录
     * @author: 王强
     * @date: 2023-06-16 15:48:55
     * @return: UserLoginVO
     * @version: 1.0
     */
    UserLoginVO loginByPhone(String dto);

    /**
     * @description: 获取用户信息
     * @author: 王强
     * @date: 2023-06-16 15:49:13
     * @return: SystemUserInfoVO
     * @version: 1.0
     */
    SystemUserInfoVO getUserInfo();
}
