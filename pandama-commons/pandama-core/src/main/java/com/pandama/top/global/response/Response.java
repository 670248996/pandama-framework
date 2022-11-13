package com.pandama.top.global.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @description: 全局响应类
 * @author: 白剑民
 * @date : 2022/7/29 10:27
 */
public class Response<T> {

    /**
     * 消息体
     */
    private T data;

    /**
     * 消息描述
     */
    private String msg;

    @JsonDeserialize(using = StatusEnumDeserializer.class)
    private ErrorResponse<T> code;

    public static <T> Response<T> success(T data) {
        return new Response(data, ResponseCode.SUCCESS);
    }

    public static <T> Response<T> success(T data, ErrorResponse<T> code) {
        return new Response(data, code);
    }

    public static <T> Response<T> success() {
        return new Response(ResponseCode.SUCCESS);
    }

    public static <T> Response<T> fail() {
        return new Response(ResponseCode.FAIL);
    }

    public static <T> Response<T> fail(String msg) {
        return new Response(msg);
    }

    public static <T> Response<T> fail(ErrorResponse<T> code) {
        return new Response(code);
    }

    public static <T> Response<T> fail(T data, ErrorResponse<T> code) {
        return new Response(data, code);
    }

    public static <T> Response<T> fail(ErrorResponse<T> code, String msg) {
        return new Response(code, msg);
    }

    public static <T> Response<T> fail(T data, String describe) {
        return new Response(data, describe);
    }

    public Response() {
    }

    public Response(T data, String describe) {
        this.data = data;
        this.msg = describe;
    }

    public Response(ErrorResponse<T> code, String describe) {
        this.code = code;
        this.msg = describe;
    }

    public Response(T data, ErrorResponse<T> code) {
        this.data = data;
        this.code = code;
        this.msg = code.getDescribe();
    }

    public Response(ErrorResponse<T> code) {
        this.code = code;
        this.msg = code.getDescribe();
    }

    public Response(String msg) {
        this.msg = msg;
    }


    public Response(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getCode() {
        return code.getCode();
    }

    public void setCode(ErrorResponse<T> code) {
        this.code = code;
    }
}
