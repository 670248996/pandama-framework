package com.pandama.top.gateway.service;

import com.pandama.top.gateway.bean.User;
import com.pandama.top.pojo.dto.PhoneNumberLoginDTO;
import com.pandama.top.pojo.dto.UsernameLoginDTO;
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
     * @param phoneNumber 电话号码
     * @description: 加载用户电话号码
     * @author: 王强
     * @date: 2023-04-22 00:14:46
     * @return: User
     * @version: 1.0
     */
    User loadUserByPhoneNumber(String phoneNumber) throws UsernameNotFoundException;
}
