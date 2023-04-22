package com.pandama.top.app.constants;

/**
 * @description: Token相关信息常量
 * @author: 王强
 * @dateTime: 2022-10-17 22:39:18
 */
public class AuthConstant {

    /**
     * redis中token格式
     */
    public static final String KEY_FORMAT = "token:%s";

    /**
     * redis中手机验证码格式
     */
    public static final String PHONE_FORMAT = "phone:%s";

    /**
     * token类型
     */
    public static final String BEARER = "Bearer ";

    /**
     * redis中token过期时间
     */
    public static final long TOKEN_EXPIRED = 8 * 60 * 60;

}
