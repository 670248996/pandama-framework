package com.pandama.top.gateway.handler;

import com.alibaba.fastjson.JSON;
import com.pandama.top.auth.api.constant.MessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * 用户身份认证成功，但无访问该资源权限的处理类
 *
 * @author 王强
 * @date 2023-07-08 15:39:21
 */
@Slf4j
@Component
public class CustomServerAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, AccessDeniedException e) {
        log.info("=====================鉴权失败=====================");
        ServerHttpResponse response = serverWebExchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.FORBIDDEN);
        HashMap<Object, Object> res = new HashMap<>(16);
        res.put(HttpStatus.FORBIDDEN.value(), MessageConstant.PERMISSION_DENIED);
        String json = JSON.toJSONString(res);
        return response.writeAndFlushWith(
                Flux.just(ByteBufFlux.just(response.bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8)))));
    }
}
