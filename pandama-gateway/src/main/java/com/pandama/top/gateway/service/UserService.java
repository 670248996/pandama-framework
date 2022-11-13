package com.pandama.top.gateway.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @description: 用户信息服务
 * @author: 王强
 * @dateTime: 2022-10-14 13:12:35
 */
public interface UserService extends UserDetailsService {

    /**
     * @param phone 手机号
     * @description: 根据手机号查询用户信息
     * @author: 王强
     * @date: 2022-10-26 20:01:18
     * @return: UserDetails
     * @version: 1.0
     */
    UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException;
}
