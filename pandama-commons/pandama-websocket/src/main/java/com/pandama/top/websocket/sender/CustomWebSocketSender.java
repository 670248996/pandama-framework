package com.pandama.top.websocket.sender;

import com.alibaba.fastjson.JSON;
import com.pandama.top.redis.utils.RedisUtils;
import com.pandama.top.websocket.constant.WebsocketConstant;
import com.pandama.top.websocket.server.WebSocketSessionManager;
import com.pandama.top.websocket.model.WebSocketMessage;
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

    private final RedisUtils redisUtils;

    public CustomWebSocketSender(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Override
    public boolean send(WebSocketMessage message) {
        boolean sendResult = false;
        // 判断当前服务节点有无Session缓存
        WebSocketSession session = WebSocketSessionManager.get(message.getSocketType(), message.getSocketId());
        if (session == null) {
            log.info("发送分布式WebSocket消息 {}", JSON.toJSONString(message));
            // 如果没有，则尝试发布redis广播通知，让其他节点进行本地session检查并发送消息
            sendResult = redisUtils.convertAndSend(WebsocketConstant.WEBSOCKET_CHANNEL, JSON.toJSONString(message)).orElse(false);
        } else {
            // 如果有，则直接发送消息
            synchronized (this) {
                try {
                    TextMessage textMessage = new TextMessage(message.getMessage());
                    session.sendMessage(textMessage);
                    sendResult = true;
                } catch (IOException e) {
                    log.error("多节点分布式WebSocket消息发送出错，错误信息: {}", e.getMessage());
                }
            }
        }
        return sendResult;
    }
}
