package com.pandama.top.websocket.Interceptor;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.pandama.top.core.global.Global;
import com.pandama.top.core.pojo.vo.CurrentUserInfo;
import com.pandama.top.core.utils.UserInfoUtils;
import com.pandama.top.starter.web.utils.IpAddressUtils;
import com.pandama.top.starter.web.utils.WebContextUtils;
import com.pandama.top.websocket.server.WebSocketSessionCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * WebSocket请求拦截器，继承此类需将类设置为有效的Spring Bean
 *
 * @author 王强
 * @date 2023-07-09 16:30:02
 */
@Slf4j
public class CustomHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public final boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                                   @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) {
        // 获得请求参数
        Map<String, String> paramMap = HttpUtil.decodeParamMap(request.getURI().getQuery(), StandardCharsets.UTF_8);
        // 业务类型
        String socketType = paramMap.get("socketType");
        // 业务唯一标识
        String socketId = paramMap.get("socketId");
        if (StringUtils.isAnyEmpty(socketType, socketId)) {
            log.error("WebSocket请求需包含业务类型[socketType]、业务唯一标识[socketId]参数");
            return false;
        }
        attributes.putAll(paramMap);
        // 设置当前用户信息
        setCurrentUserInfo();
        // 判断WebSocket连接是否已经存在
        return WebSocketSessionCache.get(socketType, socketId) == null;
    }

    @Override
    public void afterHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                               @NonNull WebSocketHandler wsHandler, Exception exception) {

    }

    private void setCurrentUserInfo() {
        // 设置当前用户信息
        try {
            HttpServletRequest request = WebContextUtils.getRequest();
            // 获取request请求IP地址
            String ipAddress = IpAddressUtils.getIpAddress(request);
            // 获取请求头中的用户信息（网关中添加）
            CurrentUserInfo userCurrentVo = JSON.parseObject(URLDecoder.decode(request.getHeader(Global.USER_INFO),
                    StandardCharsets.UTF_8.name()), CurrentUserInfo.class);
            // 用户信息中设置IP
            userCurrentVo.setIpAddress(ipAddress);
            // UserInfoUtils中设置当前用户登录信息
            UserInfoUtils.setUserInfo(userCurrentVo);
        } catch (Exception e) {
            // 请求头中无用户信息不报错，所有接口通过网关，网关放行未校验token的则请求头中无用户信息
//            return false;
        }
    }
}