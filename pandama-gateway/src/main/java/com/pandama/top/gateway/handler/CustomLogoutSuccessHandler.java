package com.pandama.top.gateway.handler;

import com.alibaba.fastjson.JSON;
import com.pandama.top.gateway.bean.UserInfo;
import com.pandama.top.gateway.constant.AuthConstant;
import com.pandama.top.global.response.Response;
import com.pandama.top.redis.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;

import java.nio.charset.StandardCharsets;

/**
 * @description: 用户登出成功后的处理类
 * @author: 王强
 * @dateTime: 2022-10-17 21:46:41
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomLogoutSuccessHandler implements ServerLogoutSuccessHandler {

    private final RedisUtils redisUtils;

    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        log.info("=====================登出成功=====================");
        // 删除redis中用户的token信息
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        String redisTokenKey = String.format(AuthConstant.KEY_FORMAT, userInfo.getUsername());
        redisUtils.delete(redisTokenKey);
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);
        Response<Object> res = Response.success();
        String json = JSON.toJSONString(res);
        return response.writeAndFlushWith(
                Flux.just(ByteBufFlux.just(response.bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8)))));
    }
}
