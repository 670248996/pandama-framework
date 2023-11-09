package com.pandama.top.user.constant;

/**
 * 常量
 *
 * @author 王强
 * @date 2023-07-08 15:40:47
 */
public interface Constants {

    /**
     * 前缀
     *
     * @author 王强
     * @date 2023-07-08 15:40:51
     */
    interface Prefix {
        /**
         * 部门
         */
        String DEPT = "DEPT";
    }

    /**
     * key值相关常量
     *
     * @author 王强
     * @date 2023-07-08 15:41:09
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
