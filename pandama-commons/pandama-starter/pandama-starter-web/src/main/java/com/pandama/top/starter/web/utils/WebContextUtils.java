package com.pandama.top.starter.web.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @description: spring上下文
 * @author: 王强
 * @dateTime: 2022-09-20 15:03:53
 */
@Slf4j
public class WebContextUtils {
    /**
     * 获取请求
     *
     * @return {@link javax.servlet.http.HttpServletRequest}
     */
    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取响应
     *
     * @return {@link HttpServletRequest}
     */
    public static HttpServletResponse getResponse(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }
}
