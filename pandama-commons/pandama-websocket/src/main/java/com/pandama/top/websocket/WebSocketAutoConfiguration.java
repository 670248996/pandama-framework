package com.pandama.top.websocket;

import com.pandama.top.redis.utils.RedisUtils;
import com.pandama.top.websocket.listener.CustomWebSocketListener;
import com.pandama.top.websocket.sender.CustomWebSocketSender;
import com.pandama.top.websocket.server.CustomWebSocketConfigurer;
import com.pandama.top.websocket.server.WebSocketSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket自动配置类
 *
 * @author 王强
 * @date 2023-07-09 16:24:57
 */
@Slf4j
@Configuration
@EnableWebSocket
@Import(WebSocketSessionManager.class)
public class WebSocketAutoConfiguration {

    /**
     * websocket服务配置, 将自动扫描@ServerEndpoint
     *
     * @return org.springframework.web.socket.server.standard.ServerEndpointExporter
     * @author 王强
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * 默认的websocket消息发送操作类
     *
     * @return com.pandama.top.websocket.operation.DefaultWebSocketSender
     * @author 王强
     */
    @Bean
    public CustomWebSocketSender customWebSocketSender(RedisUtils redisUtils) {
        return new CustomWebSocketSender(redisUtils);
    }

    /**
     * 分布式websocket消息监听器
     *
     * @return com.pandama.top.websocket.listener.WebSocketListener
     * @author 王强
     */
    @Bean
    @ConditionalOnBean(CustomWebSocketSender.class)
    public CustomWebSocketListener customWebSocketListener() {
        return new CustomWebSocketListener();
    }


    /**
     * 向WebSocket处理器注册中心注册自定义处理器与路径映射
     *
     * @param context spring上下文
     * @return org.springframework.web.socket.config.annotation.WebSocketConfigurer
     * @author 王强
     */
    @Bean
    public WebSocketConfigurer registerCustomWebSocketHandlers(ApplicationContext context) {
        return new CustomWebSocketConfigurer(context);
    }
}
