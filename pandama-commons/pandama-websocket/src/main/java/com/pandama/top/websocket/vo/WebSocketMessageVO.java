package com.pandama.top.websocket.vo;

import lombok.Builder;
import lombok.Data;

/**
 * WebSocket消息体
 *
 * @author 王强
 * @date 2023-07-09 16:24:50
 */
@Data
@Builder
public class WebSocketMessageVO {
    /**
     * socket连接类型
     */
    private String socketType;
    /**
     * socket连接唯一标识
     */
    private String socketId;
    /**
     * 消息内容
     */
    private String message;
}
