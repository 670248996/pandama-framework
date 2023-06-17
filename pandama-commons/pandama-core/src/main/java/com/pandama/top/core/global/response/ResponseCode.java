package com.pandama.top.core.global.response;

/**
 * @description: 全局状态枚举类
 * @author: 白剑民
 * @dateTime: 2022/7/8 19:05
 */
public enum ResponseCode implements ErrorResponse<Integer> {
    FAIL(500, "服务端异常"),
    SUCCESS(200, "success"),
    PARAM_ERROR(500, "参数异常"),
    ILLEGAL_JSON(500, "非法的JSON格式");

    /**
     * 异常状态码
     */
    private final Integer code;

    /**
     * 异常描述
     */
    private final String describe;

    ResponseCode(Integer code, String describe) {
        this.code = code;
        this.describe = describe;
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
