package com.pandama.top.utils;

import com.pandama.top.pojo.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NamedThreadLocal;

/**
 * @description: 用户信息工具类
 * @author: 王强
 * @dateTime: 2022-10-28 16:00:07
 */
@Slf4j
public class UserInfoUtils {

    /**
     * 当前线程存储登录用户信息
     */
    private static final ThreadLocal<UserLoginVO> USER_LOCAL = new NamedThreadLocal<>("ThreadLocal UserInfoUtils");

    /**
     * 获取当前登录用户信息
     *
     * @return {@code UserLoginVO}
     */
    public static UserLoginVO getUserInfo() {
        return USER_LOCAL.get() == null ? new UserLoginVO(): USER_LOCAL.get();
    }

    /**
     * 设置当前登录用户信息
     */
    public static void setUserInfo(UserLoginVO vo) {
        USER_LOCAL.set(vo);
    }

    /**
     * 清除当前登录用户信息（防止内存泄露）
     */
    public static void clearUserInfo() {
        USER_LOCAL.remove();
    }

    /**
     * 获取登录用户id
     *
     * @return {@code Long}
     */
    public static Long getUserId() {
        return getUserInfo().getId();
    }

    /**
     * 获取登录用户ip
     *
     * @return {@code String}
     */
    public static String getIpAddress() {
        return getUserInfo().getIpAddress();
    }
}
