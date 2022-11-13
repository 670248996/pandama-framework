package com.pandama.top.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @description: websocket自动配置类
 * @author: 白剑民
 * @dateTime: 2022/7/20 08:25
 */
@Configuration
public class WebSocketAutoConfiguration {
    /**
     * @description: websocket服务配置, 将自动扫描@ServerEndpoint
     * @author: 白剑民
     * @date: 2022-07-20 08:29:21
     * @return: org.springframework.web.socket.server.standard.ServerEndpointExporter
     * @version: 1.0
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
