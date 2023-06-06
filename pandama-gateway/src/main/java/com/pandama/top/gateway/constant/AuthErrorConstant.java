package com.pandama.top.gateway.constant;

/**
 * @description: 登录授权错误信息常量
 * @author: 王强
 * @dateTime: 2022-10-17 22:39:18
 */
public class AuthErrorConstant {

    public static final String UN_LOGIN = "请先登录系统";

    public static final String TOKEN_EXPIRED = "token已过期，请重新登录";

    public static final String ACCOUNT_DISABLED = "该账户已被禁用，请联系管理员";

    public static final String ACCOUNT_LOCKED = "该账号已被锁定，请联系管理员";

    public static final String ACCOUNT_EXPIRED = "该账号已过期，请联系管理员";

    public static final String LOGIN_EXPIRED = "登录已过期，请重新登录";

    public static final String USER_NOT_EXIT = "用户不存";

    public static final String PASSWORD_ERROR = "者密码错误";

    public static final String SMS_CODE_ERROR = "短信验证码错误或已失效，请重新获取";

    public static final String FORBIDDEN = "没有访问该资源的权限";

}
