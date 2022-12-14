package com.pandama.top.gateway.handler;

import com.alibaba.fastjson.JSON;
import com.pandama.top.global.response.Response;
import com.pandama.top.global.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;

import java.nio.charset.StandardCharsets;

/**
 * @description: 用户身份认证失败后的处理类
 * @author: 王强
 * @dateTime: 2022-10-17 21:46:28
 */
@Slf4j
@Component
public class CustomAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException e) {
        log.info("=====================认证失败=====================");
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        Response<Integer> res = Response.fail(ResponseCode.FAIL, e.getMessage());
        String json = JSON.toJSONString(res);
        return response.writeAndFlushWith(
                Flux.just(ByteBufFlux.just(response.bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8)))));
    }
}
