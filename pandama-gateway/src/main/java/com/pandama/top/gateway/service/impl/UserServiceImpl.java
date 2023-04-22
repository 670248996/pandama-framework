package com.pandama.top.gateway.service.impl;

import com.pandama.top.gateway.bean.User;
import com.pandama.top.gateway.feign.LoginFeignClient;
import com.pandama.top.gateway.service.UserService;
import com.pandama.top.pojo.dto.PhoneNumberLoginDTO;
import com.pandama.top.pojo.dto.UsernameLoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @description: 用户信息服务impl
 * @author: 王强
 * @dateTime: 2022-10-17 22:11:03
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {

    private final LoginFeignClient loginFeignClient;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = loginFeignClient.loginByUsername(username);
        user.setRoleCodeList(Arrays.asList("admin"));
        user.setUriCodeList(Arrays.asList("admin"));
        return user;
    }

    @Override
    public User loadUserByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {
        User user = loginFeignClient.loginByPhoneNumber(phoneNumber);
        user.setRoleCodeList(Arrays.asList("admin"));
        user.setUriCodeList(Arrays.asList("admin"));
        return user;
    }
}
