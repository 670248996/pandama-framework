package com.pandama.top.user.websocket;

import com.pandama.top.websocket.annotations.WebSocketEndpoint;
import com.pandama.top.websocket.handler.CustomWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Component
@WebSocketEndpoint(path = "/")
public class MyWebSocketHandler implements CustomWebSocketHandler {

    @Override
    public void onOpen(String socketType, String socketId, WebSocketSession session) {
        log.info("websocket连接开启");
    }

    @Override
    public void onClose(String socketType, String socketId, WebSocketSession session) {
        log.info("websocket连接关闭");
    }

    @Override
    public void onMessage(String socketType, String socketId, String message, WebSocketSession session) {
        log.info("websocket收到消息 {}", message);
    }

    @Override
    public void onError(String socketType, String socketId, WebSocketSession session, Throwable error) {
        log.info("websocket异常错误 {}", error.getMessage());
    }
}
