package com.pandama.top.websocket.Interceptor;

import cn.hutool.http.HttpUtil;
import com.pandama.top.websocket.server.WebSocketSessionCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * WebSocket请求拦截器，继承此类需将类设置为有效的Spring Bean
 *
 * @author 王强
 * @date 2023-07-09 16:30:02
 */
@Slf4j
public class CustomHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public final boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                                   @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) {
        // 获得请求参数
        Map<String, String> paramMap = HttpUtil.decodeParamMap(request.getURI().getQuery(), StandardCharsets.UTF_8);
        String ipAddress = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        // 业务类型
        String socketType = paramMap.get("socketType");
        // 业务唯一标识
        String socketId = paramMap.get("socketId");
        if (socketType == null || socketType.length() == 0
                || socketId == null || socketId.length() == 0) {
            log.error("WebSocket请求需包含业务类型[socketType]、业务唯一标识[socketId]参数");
            return false;
        }
        attributes.put("socketType", socketType);
        attributes.put("socketId", socketId);
        attributes.put("ipAddress", ipAddress);
        // 判断WebSocket连接是否已经存在
        boolean isConnectAlreadyExist = WebSocketSessionCache.get(socketType, socketId) != null;
        return !isConnectAlreadyExist;
    }

    @Override
    public void afterHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                               @NonNull WebSocketHandler wsHandler, Exception exception) {

    }
}