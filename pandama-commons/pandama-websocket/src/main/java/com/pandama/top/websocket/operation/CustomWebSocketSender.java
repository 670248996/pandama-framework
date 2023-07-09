package com.pandama.top.websocket.operation;

import com.pandama.top.websocket.server.WebSocketSender;
import com.pandama.top.websocket.server.WebSocketSessionCache;
import com.pandama.top.websocket.vo.WebSocketMessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * WebSocket消息发送抽象类
 *
 * @author 王强
 * @date 2023-07-09 16:23:17
 */
@Slf4j
public class CustomWebSocketSender implements WebSocketSender {

    @Override
    public boolean send(WebSocketMessageVO message) {
        boolean result = false;
        WebSocketSession session = WebSocketSessionCache.get(message.getSocketType(), message.getSocketId());
        if (session != null) {
            synchronized (this) {
                try {
                    TextMessage textMessage = new TextMessage(message.getContent());
                    session.sendMessage(textMessage);
                    result = true;
                } catch (IOException e) {
                    log.error("默认单节点WebSocket消息发送出错，错误信息: {}", e.getMessage());
                }
            }
        }
        return result;
    }
}
