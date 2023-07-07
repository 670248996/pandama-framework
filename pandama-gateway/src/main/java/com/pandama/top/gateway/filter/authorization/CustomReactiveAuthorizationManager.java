package com.pandama.top.gateway.filter.authorization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * @description: 用户通过身份验证后，会通过该处理类校验是否有该资源的访问权限
 * @author: 王强
 * @dateTime: 2022-10-17 21:47:52
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
        log.info("=====================资源鉴权=====================");
        ServerHttpRequest request = object.getExchange().getRequest();
        String uri = request.getPath().pathWithinApplication().value();
        return authentication
                // 检查是否认证通过
                .filter(Authentication::isAuthenticated)
                // 获取用户拥有的权限集合(这里是uris)
                .flatMapIterable((Function<Authentication, Iterable<? extends GrantedAuthority>>) Authentication::getAuthorities)
                // 这里放回用户拥有的权限代码(uri)会用于下面的的匹配,
                // 如果该用户有10个权限代码,那么apply(GrantedAuthority t)会被调用10次
                .map(GrantedAuthority::getAuthority)
                // 进行匹配,如果该用户有10个权限代码,那么apply(GrantedAuthority t)会被调用10次,直到匹配上为止
                .any(pattern -> true)
                .map(AuthorizationDecision::new).defaultIfEmpty(new AuthorizationDecision(false));
    }
}
