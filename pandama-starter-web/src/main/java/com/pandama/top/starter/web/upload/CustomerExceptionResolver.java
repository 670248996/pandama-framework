package com.pandama.top.starter.web.upload;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 客户异常解析器
 *
 * @author 王强
 * @date 2024-08-14 22:00:23
 */
@Log4j2
@Component
public class CustomerExceptionResolver implements HandlerExceptionResolver, Ordered {

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    @Nullable
    public ModelAndView resolveException(
            HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex) {
        log.error("接口请求异常");
        return null;
    }
}
