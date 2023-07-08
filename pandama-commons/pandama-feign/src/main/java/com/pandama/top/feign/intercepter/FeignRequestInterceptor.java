package com.pandama.top.feign.intercepter;

import com.pandama.top.core.global.Global;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Feign请求拦截器（解决header丢失问题）
 *
 * @author 王强
 * @date 2023-07-08 15:19:49
 */
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        log.info("=====================进入Feign请求拦截方法=====================");
        // 获取当前请求的 RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            // 从 RequestAttributes 中获取请求头信息
            String headerValue = (String) requestAttributes.getAttribute(Global.USER_INFO, RequestAttributes.SCOPE_REQUEST);
            if (headerValue != null) {
                // 添加自定义请求头到 Feign 请求模板中
                template.header(Global.USER_INFO, headerValue);
            }
        }
    }
}
