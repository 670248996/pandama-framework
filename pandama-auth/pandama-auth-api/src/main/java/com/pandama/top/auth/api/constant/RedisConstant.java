package com.pandama.top.auth.api.constant;

/**
 * Redis常量
 */
public class RedisConstant {

    public static final String ACCESS_TOKEN = "ACCESS_TOKEN:%s";

    public static final int ACCESS_TOKEN_EXPIRED = 8 * 60 * 60;

    public static final int REFRESH_TOKEN_EXPIRED = 24 * 60 * 60;

}
