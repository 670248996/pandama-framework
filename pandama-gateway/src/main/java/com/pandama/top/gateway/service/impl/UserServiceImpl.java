package com.pandama.top.gateway.service.impl;

import com.pandama.top.gateway.bean.User;
import com.pandama.top.gateway.service.UserService;
import com.pandama.top.core.utils.BeanConvertUtils;
import com.pandama.top.user.api.pojo.vo.UserLoginVO;
import com.pandama.top.user.api.service.UserFeignService;
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

    private final UserFeignService userFeignService;

    @Override
    public UserDetails findByUsername(String username) {
        UserLoginVO userByUsername = userFeignService.findUserByUsername(username);
        return BeanConvertUtils.convert(userByUsername, User::new).orElse(null);
    }

    @Override
    public UserDetails findByPhone(String phone) {
        return BeanConvertUtils.convert(userFeignService.findUserByPhone(phone), User::new).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UnsupportedOperationException();
    }
}
