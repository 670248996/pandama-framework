package com.pandama.top.core.utils;

import com.pandama.top.core.pojo.vo.CurrentUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NamedThreadLocal;

/**
 * 用户信息工具
 *
 * @author 王强
 * @date 2023-07-08 15:18:02
 */
@Slf4j
public class UserInfoUtils {

    /**
     * 当前线程存储登录用户信息
     */
    private static final ThreadLocal<CurrentUserInfo> USER_LOCAL = new NamedThreadLocal<>("ThreadLocal UserInfoUtils");

    /**
     * 获取当前登录用户信息
     *
     * @return com.pandama.top.core.pojo.vo.CurrentUserInfo
     * @author 王强
     */
    public static CurrentUserInfo getUserInfo() {
        return USER_LOCAL.get() == null ? new CurrentUserInfo(): USER_LOCAL.get();
    }

    /**
     * 设置当前登录用户信息
     *
     * @param vo 出参
     * @return void
     * @author 王强
     */
    public static void setUserInfo(CurrentUserInfo vo) {
        USER_LOCAL.set(vo);
    }

    /**
     * 清除当前登录用户信息（防止内存泄露）
     *
     * @return void
     * @author 王强
     */
    public static void clearUserInfo() {
        USER_LOCAL.remove();
    }

    /**
     * 获取登录用户id
     *
     * @return java.lang.Long
     * @author 王强
     */
    public static Long getUserId() {
        return getUserInfo().getId();
    }

    /**
     * 获取登录用户ip
     *
     * @return java.lang.String
     * @author 王强
     */
    public static String getIpAddress() {
        return getUserInfo().getIpAddress();
    }
}
