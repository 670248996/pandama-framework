package com.pandama.top.gateway.handler;

import com.alibaba.fastjson.JSON;
import com.pandama.top.gateway.bean.User;
import com.pandama.top.gateway.constant.AuthConstant;
import com.pandama.top.gateway.util.Md5Utils;
import com.pandama.top.gateway.util.TokenUtils;
import com.pandama.top.global.response.Response;
import com.pandama.top.redis.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @description: 自定义身份验证成功处理程序
 * @author: 王强
 * @dateTime: 2022-10-26 20:32:59
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    private final RedisUtils redisUtils;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        log.info("=====================认证成功=====================");
        User user = (User) authentication.getPrincipal();
        // 通过工具生成token
        String token = TokenUtils.createToken(user);
        // 生成存入redis的key
        String redisTokenKey = String.format(AuthConstant.KEY_FORMAT, user.getUsername());
        // 将生成的jwttoken使用MD5加密后作为value 存入redis 并设置过期时间
        redisUtils.setEx(redisTokenKey, Md5Utils.md5(token), AuthConstant.TOKEN_EXPIRED, TimeUnit.SECONDS);
        HashMap<String, String> hashMap = new HashMap<>(1);
        hashMap.put(HttpHeaders.AUTHORIZATION, AuthConstant.BEARER + token);
        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        Response<HashMap<String, String>> res = Response.success(hashMap);
        String json = JSON.toJSONString(res);
        return response.writeAndFlushWith(
                Flux.just(ByteBufFlux.just(response.bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8)))));
    }
}
