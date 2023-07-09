package com.pandama.top.websocket.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket会话缓存管理
 *
 * @author 王强
 * @date 2023-07-09 16:24:10
 */
@Slf4j
public class WebSocketSessionCache {

    /**
     * 会话唯一标识key格式化模板
     */
    private static final String KEY_FORMAT = "%s-%s";

    /**
     * 缓存所有会话信息
     */
    private static final Map<String, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();

    /**
     * 添加会话缓存
     *
     * @param socketType 业务类型
     * @param socketId   业务唯一标识
     * @param session    会话对象
     * @return void
     * @author 王强
     */
    public static void add(String socketType, String socketId, WebSocketSession session) {
        SESSIONS.put(keyFormat(socketType, socketId), session);
    }

    /**
     * 删除会话缓存
     *
     * @param socketType 业务类型
     * @param socketId   业务唯一标识
     * @return org.springframework.web.socket.WebSocketSession
     * @author 王强
     */
    public static WebSocketSession remove(String socketType, String socketId) {
        return SESSIONS.remove(keyFormat(socketType, socketId));
    }

    /**
     * 删除会话缓存并关闭连接
     *
     * @param socketType 业务类型
     * @param socketId   业务唯一标识
     * @return void
     * @author 王强
     */
    public static void removeAndClose(String socketType, String socketId) {
        WebSocketSession session = remove(socketType, socketId);
        if (session != null) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取缓存会话
     *
     * @param socketType 业务类型
     * @param socketId   业务唯一标识
     * @return org.springframework.web.socket.WebSocketSession
     * @author 王强
     */
    public static WebSocketSession get(String socketType, String socketId) {
        return SESSIONS.get(keyFormat(socketType, socketId));
    }

    /**
     * 获取当前会话连接数
     *
     * @return java.lang.Integer
     * @author 王强
     */
    public static Integer size() {
        return SESSIONS.size();
    }

    /**
     * 会话唯一标识格式化
     *
     * @param socketType 业务类型
     * @param socketId   业务唯一标识
     * @return java.lang.String
     * @author 王强
     */
    private static String keyFormat(String socketType, String socketId) {
        return String.format(KEY_FORMAT, socketType, socketId);
    }
}
