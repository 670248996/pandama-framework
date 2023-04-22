package com.pandama.top.interceptor;

import com.alibaba.fastjson.JSON;
import com.pandama.top.global.Global;
import com.pandama.top.utils.IpUtils;
import com.pandama.top.utils.UserInfoUtils;
import com.pandama.top.pojo.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * @description: 自定义请求处理拦截器
 * @author: 王强
 * @dateTime: 2022-11-30 22:38:18
 */
@Slf4j
@Component
public class CustomHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            // 获取request请求IP地址
            String ipAddress = IpUtils.getIpAddress(request);
            // 获取请求头中的用户信息（网关中添加）
            UserLoginVO userCurrentVo = JSON.parseObject(URLDecoder.decode(request.getHeader(Global.USER_INFO), "UTF-8"), UserLoginVO.class);
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
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // UserInfoUtils中设置清除用户登录信息
        UserInfoUtils.clearUserInfo();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
