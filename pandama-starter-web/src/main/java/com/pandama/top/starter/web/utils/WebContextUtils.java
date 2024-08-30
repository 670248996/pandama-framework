package com.pandama.top.starter.web.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * spring上下文
 *
 * @author 王强
 * @date 2023-07-08 15:34:52
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
