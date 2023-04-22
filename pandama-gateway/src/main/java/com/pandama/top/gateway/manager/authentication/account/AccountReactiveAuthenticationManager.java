package com.pandama.top.gateway.manager.authentication.account;

import com.pandama.top.gateway.constant.AuthErrorConstant;
import com.pandama.top.gateway.manager.authentication.BaseAuthenticationManager;
import com.pandama.top.gateway.service.UserService;
import com.pandama.top.pojo.dto.UsernameLoginDTO;
import com.pandama.top.utils.BeanConvertUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @description: 账号登录会通过该处理类校验账号密码及账号信息
 * @author: 王强
 * @dateTime: 2022-10-17 21:46:28
 */
@Slf4j
@Primary
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AccountReactiveAuthenticationManager implements BaseAuthenticationManager<AccountAuthentication> {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<Authentication> authenticate(AccountAuthentication authentication) {
        log.info("=====================用户名登录认证=====================");
        UserDetails user;
        try {
            // 获取用户信息
            user = userService.loadUserByUsername(authentication.getUsername());
        } catch (UsernameNotFoundException ufe) {
            return Mono.error(ufe);
        }
        // 校验密码
        if (Objects.isNull(user) || !passwordEncoder.matches(authentication.getPassword(), passwordEncoder.encode(user.getPassword()))) {
            return Mono.error(new BadCredentialsException(AuthErrorConstant.PASSWORD_ERROR));
        }
        // 判断用户是否禁用
        else if (!user.isEnabled()) {
            return Mono.error(new DisabledException(AuthErrorConstant.ACCOUNT_DISABLED));
        }
        // 判断用户是否锁定
        else if (!user.isAccountNonLocked()) {
            return Mono.error(new LockedException(AuthErrorConstant.ACCOUNT_LOCKED));
        }
        // 判断账号是否过期
        else if (!user.isAccountNonExpired()) {
            return Mono.error(new AccountExpiredException(AuthErrorConstant.ACCOUNT_EXPIRED));
        }
        // 判断登陆是否过期
        else if (!user.isCredentialsNonExpired()) {
            return Mono.error(new CredentialsExpiredException(AuthErrorConstant.LOGIN_EXPIRED));
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(user, authentication.getPassword(), user.getAuthorities());
        // WebFlux方式默认没有放到context中，需要手动放入
        SecurityContextHolder.getContext().setAuthentication(auth);
        return Mono.just(auth);
    }
}
