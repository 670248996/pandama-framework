package com.pandama.top.gateway.filter.authentication;

import com.alibaba.fastjson.JSON;
import com.pandama.top.cache.utils.RedisUtils;
import com.pandama.top.gateway.constant.AuthConstant;
import com.pandama.top.gateway.constant.AuthErrorConstant;
import com.pandama.top.gateway.util.Md5Utils;
import com.pandama.top.gateway.util.TokenUtils;
import com.pandama.top.global.Global;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @description: 令牌身份验证过滤器
 * @author: 王强
 * @dateTime: 2022-10-26 14:58:06
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TokenAuthenticationFilter implements WebFilter {

    private final RedisUtils redisUtils;

    @Override
    @SuppressWarnings("all")
    public @NonNull Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 获取请求头Authorization信息
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        // 判断是否是BearerToken请求
        if (authHeader != null && authHeader.startsWith(AuthConstant.BEARER)) {
            // 执行Token请求认证
            log.info("=====================Token认证=====================");
            // 截取Token信息
            String authToken = authHeader.substring(AuthConstant.BEARER.length());
            // 校验token, 并获取用户信息
            UserDetails user = TokenUtils.validationToken(authToken);
            if (user == null) {
                return Mono.error(new CredentialsExpiredException(AuthErrorConstant.TOKEN_EXPIRED));
            }
            // 校验redis中的token
            String redisTokenKey = String.format(AuthConstant.KEY_FORMAT, user.getUsername());
            if (!Md5Utils.md5(authToken).equals(redisUtils.get(redisTokenKey).orElse(""))) {
                return Mono.error(new CredentialsExpiredException(AuthErrorConstant.TOKEN_EXPIRED));
            }
            // 验证通过, 刷新token有效时间
            redisUtils.expire(redisTokenKey, AuthConstant.TOKEN_EXPIRED, TimeUnit.SECONDS);
            Authentication auth = new UsernamePasswordAuthenticationToken(user, authToken, user.getAuthorities());
            try {
                // 将用户信息放入Header中, URL加密解决中文乱码
                request.mutate()
                        .header(Global.USER_INFO, URLEncoder.encode(JSON.toJSONString(user), StandardCharsets.UTF_8.name()))
                        .build();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
        } else {
            return chain.filter(exchange);
        }
    }
}
