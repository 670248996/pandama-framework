package com.pandama.top.websocket.handler;


import com.pandama.top.core.global.exception.CommonException;
import com.pandama.top.websocket.server.WebSocketSessionCache;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.*;

/**
 * 自定义WebSocket消息处理器接口
 *
 * @author 王强
 * @date 2023-07-09 16:21:56
 */
public interface CustomWebSocketHandler extends WebSocketHandler {

    /**
     * 当websocket连接打开时执行
     *
     * @param socketType socket类型
     * @param socketId   socket唯一标识
     * @param session    socket会话
     * @return void
     * @author 王强
     */
    void onOpen(String socketType, String socketId, WebSocketSession session);

    /**
     * 当websocket连接关闭时执行
     *
     * @param socketType socket类型
     * @param socketId   socket唯一标识
     * @param session    socket会话
     * @return void
     * @author 王强
     */
    void onClose(String socketType, String socketId, WebSocketSession session);

    /**
     * 当websocket服务端收到消息时执行
     *
     * @param socketType socket类型
     * @param socketId   socket唯一标识
     * @param message    websocket服务端接收到的消息
     * @param session    socket会话
     * @return void
     * @author 王强
     */
    void onMessage(String socketType, String socketId, String message, WebSocketSession session);

    /**
     * 当websocket连接产生异常时执行
     *
     * @param socketType socket类型
     * @param socketId   socket唯一标识
     * @param session    socket会话
     * @param error      socket异常
     * @return void
     * @author 王强
     */
    void onError(String socketType, String socketId, WebSocketSession session, Throwable error);

    /**
     * 连接建立之后
     *
     * @param session 会话
     * @return void
     * @author 王强
     */
    @Override
    default void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        // 业务类型
        String socketType = String.valueOf(session.getAttributes().get("socketType"));
        // 业务唯一标识
        String socketId = String.valueOf(session.getAttributes().get("socketId"));
        this.onOpen(socketType, socketId, session);
        WebSocketSessionCache.add(socketType, socketId, session);
    }

    /**
     * 处理消息
     *
     * @param session 会话
     * @param message 消息
     * @return void
     * @author 王强
     */
    @Override
    default void handleMessage(@NonNull WebSocketSession session, @NonNull WebSocketMessage<?> message) throws Exception {
        // 业务类型
        String socketType = String.valueOf(session.getAttributes().get("socketType"));
        // 业务唯一标识
        String socketId = String.valueOf(session.getAttributes().get("socketId"));
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            this.onMessage(socketType, socketId, textMessage.getPayload(), session);
        } else {
            throw new CommonException("不支持的WebSocket类型");
        }
    }

    /**
     * 连接关闭后
     *
     * @param session     会话
     * @param closeStatus 关闭状态
     * @return void
     * @author 王强
     */
    @Override
    default void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus closeStatus) throws Exception {
        // 业务类型
        String socketType = String.valueOf(session.getAttributes().get("socketType"));
        // 业务唯一标识
        String socketId = String.valueOf(session.getAttributes().get("socketId"));
        this.onClose(socketType, socketId, session);
        WebSocketSessionCache.remove(socketType, socketId);
    }

    /**
     * 处理运输错误
     *
     * @param session   会话
     * @param exception 异常
     * @return void
     * @author 王强
     */
    @Override
    default void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) throws Exception {
        // 业务类型
        String socketType = String.valueOf(session.getAttributes().get("socketType"));
        // 业务唯一标识
        String socketId = String.valueOf(session.getAttributes().get("socketId"));
        this.onError(socketType, socketId, session, exception);
        WebSocketSessionCache.removeAndClose(socketType, socketId);
    }

    /**
     * 支持部分信息
     *
     * @return boolean
     * @author 王强
     */
    @Override
    default boolean supportsPartialMessages() {
        return false;
    }
}
