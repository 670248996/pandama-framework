package com.pandama.top.gateway.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @description: MD5加密工具类
 * @author: 王强
 * @dateTime: 2022-10-24 12:26:44
 */
public class Md5Utils {

    /**
     * @param plainText 需要加密的数据
     * @description: 对文本执行 md5 摘要加密
     * @author: 王强
     * @date: 2022-10-24 13:17:47
     * @return: String
     * @version: 1.0
     */
    public static String md5(String plainText) {
        if (null == plainText) {
            plainText = "";
        }
        String md5Str = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] b = md.digest();
            int i;
            StringBuilder builder = new StringBuilder(32);
            for (byte value : b) {
                i = value;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    builder.append("0");
                }
                builder.append(Integer.toHexString(i));
            }
            md5Str = builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5Str;
    }
}