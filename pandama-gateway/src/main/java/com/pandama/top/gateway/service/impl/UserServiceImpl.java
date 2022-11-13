package com.pandama.top.gateway.service.impl;

import com.pandama.top.gateway.bean.User;
import com.pandama.top.gateway.feign.LoginFeignClient;
import com.pandama.top.gateway.service.UserService;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO feign获取用户信息, 暂时模拟假数据
//        User user = loginFeignClient.findUserByUsername(username);
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword("123456");
        user.setCode("admin");
        user.setRealName("王强");
        user.setPhone("18205055957");
        user.setIsAdmin(true);
        user.setRoleCodeList(Arrays.asList("admin", "user"));
        user.setPermCodeList(Arrays.asList("insert", "delete"));
        user.setUriCodeList(Arrays.asList("/auth/login", "/auth/userInfo"));
        return user;
    }

    @Override
    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        // TODO feign获取用户信息, 暂时模拟假数据
//        User user = loginFeignClient.findUserByPhone(phone);
        User user = new User();
        user.setId(2L);
        user.setUsername("admin");
        user.setPassword("123456");
        user.setCode("admin");
        user.setRealName("王强");
        user.setPhone("18205055957");
        user.setIsAdmin(true);
        user.setRoleCodeList(Arrays.asList("admin", "user"));
        user.setPermCodeList(Arrays.asList("insert", "delete"));
        user.setUriCodeList(Arrays.asList("/auth/login", "/auth/userInfo"));
        return user;
    }
}
