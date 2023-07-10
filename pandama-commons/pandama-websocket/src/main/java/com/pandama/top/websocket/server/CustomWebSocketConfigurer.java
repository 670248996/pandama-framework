package com.pandama.top.websocket.server;

import com.pandama.top.websocket.Interceptor.CustomHandshakeInterceptor;
import com.pandama.top.websocket.handler.CustomWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket处理器初始化
 *
 * @author 王强
 * @date 2023-07-09 16:23:29
 */
@Slf4j
public class CustomWebSocketConfigurer implements WebSocketConfigurer {

    /**
     * WebSocket路径默认前缀
     */
    private static final String PREFIX = "/ws";

    /**
     * WebSocket请求路径前缀
     */
    private static final String PATH_PREFIX = "/";

    private final ApplicationContext context;

    public CustomWebSocketConfigurer(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
        Map<String, String> handlerMap = new HashMap<>(16);
        // 获取所有实现了WebSocket处理器接口的实现类
        Map<String, CustomWebSocketHandler> handlers = context.getBeansOfType(CustomWebSocketHandler.class);
        // 校验这些实现类是否包含规定注解
        handlers.forEach((beanName, handler) -> {
            if (StringUtils.isNotBlank(handler.path()) && !handler.path().startsWith(PATH_PREFIX)) {
                throw new RuntimeException(beanName + "监听路径需以'/'开头");
            }
            String path = PREFIX + handler.path();
            if (handlerMap.containsKey(path)) {
                throw new RuntimeException(beanName + "与" + handlerMap.get(path) + "监听路径重复");
            }
            handlerMap.put(path, beanName);
            // 注册WebSocket处理器
            registry.addHandler(handler, path)
                    .addInterceptors(new CustomHandshakeInterceptor())
                    .setAllowedOrigins("*");
            log.info("WebSocket监听器注册成功 连接地址: " + path);
        });
    }
}