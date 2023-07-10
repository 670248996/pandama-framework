package com.pandama.top.user.websocket;

import com.pandama.top.websocket.annotation.WebSocketListener;
import com.pandama.top.websocket.handler.CustomWebSocketHandler;
import com.pandama.top.websocket.sender.WebSocketSender;
import com.pandama.top.websocket.model.WebSocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * 我的websocket处理程序
 *
 * @author 王强
 * @date 2023-07-10 09:33:12
 */
@Slf4j
@Component
@WebSocketListener
public class MyWebSocketHandler implements CustomWebSocketHandler {

    @Autowired
    private WebSocketSender webSocketSender;

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
        WebSocketMessage build = WebSocketMessage.builder().socketType("1").socketId("2").message("123").build();
        webSocketSender.send(build);
    }

    @Override
    public void onError(String socketType, String socketId, WebSocketSession session, Throwable error) {
        log.info("websocket异常错误 {}", error.getMessage());
    }
}
