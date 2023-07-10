package com.pandama.top.websocket.sender;

import com.pandama.top.websocket.model.WebSocketMessage;

/**
 * WebSocket消息发送接口
 *
 * @author 王强
 * @date 2023-07-09 16:24:00
 */
public interface WebSocketSender {

    /**
     * 发送
     *
     * @param message socket消息体
     * @return boolean
     * @author 王强
     */
    boolean send(WebSocketMessage message);
}
