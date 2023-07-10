package com.pandama.top.websocket.listener;

import com.alibaba.fastjson.JSON;
import com.pandama.top.redis.annotation.RedisListener;
import com.pandama.top.websocket.constant.WebsocketConstant;
import com.pandama.top.websocket.server.WebSocketSessionManager;
import com.pandama.top.websocket.model.WebSocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 基于redis广播消息实现的分布式websocket消息监听器
 *
 * @author 王强
 * @date 2023-07-10 13:58:34
 */
@Slf4j
@RedisListener(value = WebsocketConstant.WEBSOCKET_CHANNEL)
public class CustomWebSocketListener implements MessageListener {

    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        // 序列化消息体
        String msg = JSON.parseObject(new String(message.getBody(), StandardCharsets.UTF_8), String.class);
        if (StringUtils.isNotBlank(msg)) {
            log.info("接收到分布式WebSocket消息 {}", msg);
            WebSocketMessage messageVO = JSON.parseObject(msg, WebSocketMessage.class);
            // 判断当前服务节点有无Session缓存
            WebSocketSession session = WebSocketSessionManager.get(messageVO.getSocketType(), messageVO.getSocketId());
            // 如果有则执行消息发送至目标websocket连接上
            if (session != null) {
                // 在高并发下可能出现session被多个线程共用导致socket状态异常的问题，这里需要加同步锁
                synchronized (this) {
                    try {
                        TextMessage textMessage = new TextMessage(messageVO.getMessage());
                        session.sendMessage(textMessage);
                    } catch (IOException e) {
                        log.error("分布式WebSocket消息发送出错，错误信息: {}", e.getMessage());
                    }
                }
            }
        }
    }
}
