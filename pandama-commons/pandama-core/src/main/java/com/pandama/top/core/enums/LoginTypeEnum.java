package com.pandama.top.core.enums;

/**
 * 登录枚举类型
 *
 * @author 王强
 * @date 2023-07-08 15:12:37
 */
public enum LoginTypeEnum {
    /**
     * 登录
     */
    LOGIN("100001", "登录"),
    /**
     * 退出
     */
    LOGOUT("100002", "退出"),
    ;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private final String code;

    private final String name;

    LoginTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(String code) {
        for (LoginTypeEnum value : LoginTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.getName();
            }
        }
        return "";
    }

}
