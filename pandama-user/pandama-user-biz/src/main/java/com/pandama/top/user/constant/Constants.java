package com.pandama.top.user.constant;

/**
 * @description: 用户常量
 * @author: 王强
 * @dateTime: 2023-05-23 20:45:38
 */
public interface Constants {

    /**
     * @description: 前缀
     * @author: 王强
     * @dateTime: 2023-06-17 09:38:38
     */
    interface Prefix {
        /**
         * 部门
         */
        String DEPT = "DEPT";
    }

    /**
     * @description: key值相关常量
     * @author: hyj
     * @dateTime: 2022/11/29 17:37
     */
    interface Redis {
        /**
         * 锁前缀
         */
        String LOCK_KEY = "lock:%s";

        /**
         * 基础数据前缀
         **/
        String BASE_KEY = "base:%s";
    }
}
