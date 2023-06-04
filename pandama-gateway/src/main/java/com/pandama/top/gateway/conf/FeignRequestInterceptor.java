package com.pandama.top.gateway.conf;

import com.pandama.top.global.Global;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: Feign请求拦截器（解决header丢失问题）
 * @author: 王强
 * @dateTime: 2023-02-22 22:20:46
 */
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            // 重新设置请求头信息
            HttpServletRequest request = requestAttributes.getRequest();
            template.header(Global.USER_INFO, request.getHeader(Global.USER_INFO));
        }
    }
}
