package com.pandama.top.starter.web.intercepter;

import com.alibaba.fastjson.JSON;
import com.pandama.top.core.global.Global;
import com.pandama.top.core.pojo.vo.CurrentUserInfo;
import com.pandama.top.core.utils.UserInfoUtils;
import com.pandama.top.starter.web.utils.IpAddressUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * 自定义请求处理拦截器
 *
 * @author 王强
 * @date 2023-07-08 15:32:44
 */
@Slf4j
@Component
public class CustomHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        try {
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
//            throw new CommonException(ResponseCode.GATEWAY_UN_PASS);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request,
                           @NonNull HttpServletResponse response,
                           @NonNull Object handler,
                           ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                Exception ex) throws Exception {
        // UserInfoUtils中设置清除用户登录信息
        UserInfoUtils.clearUserInfo();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
