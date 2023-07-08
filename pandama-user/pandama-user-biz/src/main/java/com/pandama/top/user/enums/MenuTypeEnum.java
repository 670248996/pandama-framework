package com.pandama.top.user.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 菜单枚举类型
 *
 * @author 王强
 * @date 2023-07-08 15:47:44
 */
public enum MenuTypeEnum {
    /**
     * 目录权限
     */
    DIR,
    /**
     * 菜单权限
     */
    MENU,
    /**
     * 按钮权限
     */
    BUTTON;

    /**
     * 是否路由器
     *
     * @return java.lang.Boolean
     * @author 王强
     */
    public Boolean isRouter() {
        List<MenuTypeEnum> routerTypeEnums = Arrays.asList(DIR, MENU);
        return routerTypeEnums.contains(this);
    }
}
