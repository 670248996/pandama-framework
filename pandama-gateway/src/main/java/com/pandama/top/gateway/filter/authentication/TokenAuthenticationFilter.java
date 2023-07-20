package com.pandama.top.gateway.filter.authentication;

import com.alibaba.fastjson.JSON;
import com.nimbusds.jose.JWSObject;
import com.pandama.top.auth.api.constant.AuthConstant;
import com.pandama.top.auth.api.constant.MessageConstant;
import com.pandama.top.auth.api.constant.RedisConstant;
import com.pandama.top.core.global.Global;
import com.pandama.top.redis.utils.RedisUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

/**
 * 令牌身份验证过滤器
 *
 * @author 王强
 * @date 2023-07-08 15:38:33
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 2)
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
            //从token中解析用户信息并设置到Header中去
            String authToken = authHeader.substring(AuthConstant.BEARER.length());
            LoginUser user = null;
            try {
                JWSObject jwsObject = JWSObject.parse(authToken);
                user = JSON.parseObject(jwsObject.getPayload().toString(), LoginUser.class);
            } catch (ParseException e) {
                return Mono.error(new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED));
            }
            // 校验redis中的token
            String redisTokenKey = String.format(RedisConstant.ACCESS_TOKEN, user.getId());
            if (!authToken.equals(redisUtils.get(redisTokenKey).orElse(""))) {
                return Mono.error(new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED));
            }
            // 校验用户权限
            if (CollectionUtils.isEmpty(user.getAuthorities())) {
                return Mono.error(new CredentialsExpiredException(MessageConstant.PERMISSION_DENIED));
            }
            Authentication auth = new UsernamePasswordAuthenticationToken(user, user.getId(), user.getGrantedAuthorities());
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
