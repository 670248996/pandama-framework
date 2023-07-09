package com.pandama.top.websocket.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * WebSocket监听服务端点
 *
 * @author 王强
 * @date 2023-07-09 16:21:34
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface WebSocketEndpoint {

    /**
     * WebSocket监听路径
     *
     * @return java.lang.String
     * @author 王强
     */
    String path();

}
