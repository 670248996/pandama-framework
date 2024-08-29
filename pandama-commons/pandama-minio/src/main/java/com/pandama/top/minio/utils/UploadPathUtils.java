package com.pandama.top.minio.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * 上传路径工具
 *
 * @author 王强
 * @date 2023-07-08 15:25:23
 */
@Slf4j
public class UploadPathUtils {

    /**
     * 获取常见上传路径
     *
     * @param fileName 文件名称
     * @return java.lang.String
     * @author 王强
     */
    public static String getCommonUploadPath(String fileName) {
        // upload/年/月/日/时/fileName
        return getUploadPath("upload", fileName);
    }

    /**
     * 获取上传路径
     *
     * @param prefix   前缀
     * @param fileName 文件名称
     * @return java.lang.String
     * @author 王强
     */
    public static String getUploadPath(String prefix, String fileName) {
        // prefix/年/月/日/时/fileName
        String format = "/%s/%s/%s/%s/%s/%s";
        LocalDateTime localDateTime = LocalDateTime.now();
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int day = localDateTime.getDayOfMonth();
        int hour = localDateTime.getHour();
        return String.format(format, prefix, year, month, day, hour, fileName);
    }
}