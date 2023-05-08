package com.pandama.top.gateway.service.impl;

import com.pandama.top.app.feign.LoginFeignClient;
import com.pandama.top.app.pojo.vo.UserLoginVO;
import com.pandama.top.gateway.bean.User;
import com.pandama.top.gateway.service.UserService;
import com.pandama.top.utils.BeanConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

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
        UserLoginVO userLoginVO = loginFeignClient.loginByUsername(username);
        User user = BeanConvertUtils.convert(userLoginVO, User::new).orElse(new User());
        user.setRoleCodeList(Collections.singletonList("admin"));
        user.setUriCodeList(Collections.singletonList("admin"));
        return user;
    }

    @Override
    public User loadUserByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {
        UserLoginVO userLoginVO = loginFeignClient.loginByPhoneNumber(phoneNumber);
        User user = BeanConvertUtils.convert(userLoginVO, User::new).orElse(new User());
        user.setRoleCodeList(Collections.singletonList("admin"));
        user.setUriCodeList(Collections.singletonList("admin"));
        return user;
    }
}
