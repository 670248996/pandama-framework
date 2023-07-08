package com.pandama.top.core.global.exception;

import org.apache.http.HttpStatus;

/**
 * 常见异常
 *
 * @author 王强
 * @date 2023-07-08 15:12:41
 */
public class CommonException extends RuntimeException {
    /**
     * 错误码
     */
    private final Integer code;
    /**
     * 错误描述
     */
    private final String msg;

    public CommonException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CommonException(String msg) {
        this.code = HttpStatus.SC_INTERNAL_SERVER_ERROR;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
