package com.pandama.top.user.enums;

import java.util.Arrays;
import java.util.List;

/**
 * @description: 菜单类型枚举类
 * @author: 白剑民
 * @date : 2023/04/24 13:10
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
     * @description: 是否是路由类型
     * @author: 王强
     * @date: 2023-04-25 12:29:53
     * @return: Boolean
     * @version: 1.0
     */
    public Boolean isRouter() {
        List<MenuTypeEnum> routerTypeEnums = Arrays.asList(DIR, MENU);
        return routerTypeEnums.contains(this);
    }
}