package com.pandama.top.global.exception;

import com.pandama.top.global.response.ErrorResponse;
import com.pandama.top.global.response.ResponseCode;

/**
 * @description: 全局自定义异常类
 * @author: 白剑民
 * @dateTime: 2022/10/12 15:59
 */
public class CommonException extends RuntimeException implements ErrorResponse<Integer> {
    /**
     * 错误码
     */
    private final Integer code;
    /**
     * 错误描述
     */
    private final String describe;

    public CommonException(Integer code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public CommonException(String describe) {
        this.code = ResponseCode.FAIL.getCode();
        this.describe = describe;
    }

    public CommonException(ErrorResponse<Integer> errorResponse) {
        this.code = errorResponse.getCode();
        this.describe = errorResponse.getDescribe();
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDescribe() {
        return describe;
    }
}
