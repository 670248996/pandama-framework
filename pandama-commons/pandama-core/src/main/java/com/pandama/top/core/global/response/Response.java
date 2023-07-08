package com.pandama.top.core.global.response;

import org.apache.http.HttpStatus;

/**
 * 响应
 *
 * @author 王强
 * @date 2023-07-08 15:12:46
 */
public class Response<T> {

    private Integer code;

    private String msg;

    private T data;

    public static <T> Response<T> success() {
        return success(null);
    }

    public static <T> Response<T> success(String msg) {
        return success(msg, null);
    }

    public static <T> Response<T> success(T data) {
        return success("请求成功", data);
    }

    public static <T> Response<T> success(String msg, T data) {
        return new Response<>(HttpStatus.SC_OK, msg, data);
    }

    public static <T> Response<T> fail() {
        return fail(null);
    }

    public static <T> Response<T> fail(String msg) {
        return fail(msg, null);
    }

    public static <T> Response<T> fail(T data) {
        return fail("请求失败", data);
    }

    public static <T> Response<T> fail(String msg, T data) {
        return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg, data);
    }

    public Response(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
