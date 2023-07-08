package com.pandama.top.user.enums;

import lombok.Getter;

/**
 * 终端类型枚举
 *
 * @author 王强
 * @date 2023-07-08 15:47:51
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
