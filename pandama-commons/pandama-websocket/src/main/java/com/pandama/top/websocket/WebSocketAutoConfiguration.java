package com.pandama.top.websocket;

import com.pandama.top.websocket.operation.CustomWebSocketSender;
import com.pandama.top.websocket.server.CustomWebSocketConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public CustomWebSocketSender customWebSocketSender() {
        return new CustomWebSocketSender();
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
