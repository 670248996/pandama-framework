package com.pandama.top.gateway.service.impl;

import com.pandama.top.app.feign.UserFeignClient;
import com.pandama.top.app.pojo.vo.UserLoginVO;
import com.pandama.top.gateway.bean.User;
import com.pandama.top.gateway.constant.AuthErrorConstant;
import com.pandama.top.gateway.service.UserService;
import com.pandama.top.utils.BeanConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
    public Mono<UserDetails> findByUsername(String username) {
        return userFeignClient.loginByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException(AuthErrorConstant.USER_NOT_EXIT)))
                .flatMap((user) -> Mono.just(BeanConvertUtils.convert(user, User::new).orElse(new User())));
    }

    @Override
    public Mono<UserDetails> findByPhoneNumber(String phoneNumber) {
        return userFeignClient.loginByPhoneNumber(phoneNumber)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException(AuthErrorConstant.USER_NOT_EXIT)))
                .flatMap((user) -> Mono.just(BeanConvertUtils.convert(user, User::new).orElse(new User())));
    }
}
