package com.pandama.top.auth.exception;

import com.pandama.top.core.global.response.Response;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局处理Oauth2抛出的异常
 *
 * @author 王强
 * @date 2023-07-08 11:53:42
 */
@ControllerAdvice
public class Oauth2ExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = OAuth2Exception.class)
    public Response<?> handleOauth2(OAuth2Exception e) {
        return Response.fail(e.getMessage());
    }
}
