package com.pandama.top.user.api.enums;

import lombok.Getter;

/**
 * @description: 权限类型枚举类
 * @author: 白剑民
 * @date : 2023/04/24 13:30
 */
@Getter
public enum TerminalTypeEnum {
    /**
     * PC端
     */
    PC,
    /**
     * 微信小程序端
     */
    WECHAT_MINI_APP,
    /**
     * IOS端手机APP
     */
    IOS_APP,
    /**
     * 安卓端手机APP
     */
    ANDROID_APP,
    ;

}
