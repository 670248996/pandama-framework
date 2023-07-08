package com.pandama.top.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5工具
 *
 * @author 王强
 * @date 2023-07-08 15:15:50
 */
public class MD5Utils {

    /**
     * md5
     *
     * @param plainText 需要加密的数据
     * @return java.lang.String
     * @author 王强
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