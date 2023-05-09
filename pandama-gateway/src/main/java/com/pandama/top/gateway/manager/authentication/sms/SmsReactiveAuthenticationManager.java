package com.pandama.top.gateway.manager.authentication.sms;


import com.pandama.top.gateway.bean.UserInfo;
import com.pandama.top.gateway.constant.AuthConstant;
import com.pandama.top.gateway.constant.AuthErrorConstant;
import com.pandama.top.gateway.manager.authentication.BaseAuthenticationManager;
import com.pandama.top.gateway.service.UserService;
import com.pandama.top.global.exception.CommonException;
import com.pandama.top.redis.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @description: 短信登录会通过该处理类校验账号密码及账号信息
 * @author: 王强
 * @dateTime: 2022-10-17 21:46:28
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SmsReactiveAuthenticationManager implements BaseAuthenticationManager<SmsAuthentication> {
    private final UserService userService;

    private final RedisUtils redisUtils;

    @Override
    public Mono<Authentication> authenticate(SmsAuthentication authentication) {
        log.info("=====================手机号登录认证=====================");
        String smsCodeKey = String.format(AuthConstant.PHONE_FORMAT, authentication.getPhoneNumber());
        if (!redisUtils.get(smsCodeKey).orElse("").equals(authentication.getSmsCode())) {
            return Mono.error(new CommonException(AuthErrorConstant.SMS_CODE_ERROR));
        }
        UserInfo userInfo;
        try {
            // 获取用户信息
            userInfo = userService.loadUserByPhoneNumber(authentication.getPhoneNumber());
        } catch (UsernameNotFoundException ufe) {
            return Mono.error(ufe);
        }
        // 判断用户是否禁用
        if (!userInfo.isEnabled()) {
            return Mono.error(new DisabledException(AuthErrorConstant.ACCOUNT_DISABLED));
        }
        // 判断用户是否锁定
        else if (!userInfo.isAccountNonLocked()) {
            return Mono.error(new LockedException(AuthErrorConstant.ACCOUNT_LOCKED));
        }
        // 判断账号是否过期
        else if (!userInfo.isAccountNonExpired()) {
            return Mono.error(new AccountExpiredException(AuthErrorConstant.ACCOUNT_EXPIRED));
        }
        // 判断登陆是否过期
        else if (!userInfo.isCredentialsNonExpired()) {
            return Mono.error(new CredentialsExpiredException(AuthErrorConstant.LOGIN_EXPIRED));
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(userInfo, authentication.getSmsCode(), userInfo.getAuthorities());
        // WebFlux方式默认没有放到context中，需要手动放入
        SecurityContextHolder.getContext().setAuthentication(auth);
        return Mono.just(auth);
    }
}
