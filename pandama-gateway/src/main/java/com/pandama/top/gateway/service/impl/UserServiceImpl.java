package com.pandama.top.gateway.service.impl;

import com.pandama.top.app.feign.UserFeignClient;
import com.pandama.top.app.pojo.vo.UserLoginVO;
import com.pandama.top.gateway.bean.User;
import com.pandama.top.gateway.service.UserService;
import com.pandama.top.utils.BeanConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @description: 用户信息服务impl
 * @author: 王强
 * @dateTime: 2022-10-17 22:11:03
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {

    private final UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLoginVO userLoginVO = userFeignClient.loginByUsername(username);
        return BeanConvertUtils.convert(userLoginVO, User::new).orElse(new User());
    }

    @Override
    public UserDetails loadUserByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {
        UserLoginVO userLoginVO = userFeignClient.loginByPhoneNumber(phoneNumber);
        return BeanConvertUtils.convert(userLoginVO, User::new).orElse(new User());
    }
}
