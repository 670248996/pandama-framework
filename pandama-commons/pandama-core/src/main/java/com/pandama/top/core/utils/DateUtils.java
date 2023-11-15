package com.pandama.top.core.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具
 *
 * @author 王强
 * @date 2023-07-08 15:13:57
 */
public class DateUtils {

    /**
     * 默认datetime格式器
     */
    private static final DateTimeFormatter DEFAULT_FORMATTER = TimeFormat.YMD_HMS_LINE.formatter;

    /**
     * 无参数的构造函数,防止被实例化
     */
    private DateUtils() {}

    /**
     * 获取当前时间
     *
     * @return java.lang.String
     * @author 王强
     */
    public static String now() {
        return DEFAULT_FORMATTER.format(LocalDateTime.now());
    }

    /**
     * 获取当前时间
     *
     * @return java.lang.String
     * @author 王强
     */
    public static String getCurrentDateTime() {
        return DEFAULT_FORMATTER.format(LocalDateTime.now());
    }

    /**
     * String 转化为 LocalDateTime
     *
     * @param timeStr 被转化的字符串
     * @return java.time.LocalDateTime
     * @author 王强
     */
    public static LocalDateTime parseTime(String timeStr) {
        return LocalDateTime.parse(timeStr, DEFAULT_FORMATTER);

    }

    /**
     * String 转化为 LocalDateTime
     *
     * @param timeStr    被转化的字符串
     * @param timeFormat 转化的时间格式
     * @return java.time.LocalDateTime
     * @author 王强
     */
    public static LocalDateTime parseTime(String timeStr, TimeFormat timeFormat) {
        return LocalDateTime.parse(timeStr, timeFormat.formatter);

    }

    /**
     * LocalDateTime 转化为String
     *
     * @param time LocalDateTime
     * @return java.lang.String
     * @author 王强
     */
    public static String parseTime(LocalDateTime time) {
        return DEFAULT_FORMATTER.format(time);

    }

    /**
     * LocalDateTime 时间转 String
     *
     * @param timeFormat 时间格式
     * @return java.lang.String
     * @author 王强
     */
    public static String parseTime(TimeFormat timeFormat) {
        return timeFormat.formatter.format(LocalDateTime.now());
    }

    /**
     * LocalDateTime 时间转 String
     *
     * @param time   LocalDateTime
     * @param format 时间格式
     * @return java.lang.String
     * @author 王强
     */
    public static String parseTime(LocalDateTime time, TimeFormat format) {
        return format.formatter.format(time);
    }

    /**
     * 获取一天开始时间
     *
     * @param d d
     * @return java.util.Date
     * @author 王强
     */
    public static Date getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,
                0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Date(calendar.getTimeInMillis());
    }


    /**
     * 获取一天结束时间
     *
     * @param d d
     * @return java.util.Date
     * @author 王强
     */
    public static Date getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23,
                59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 内部枚举类
     *
     * @author 王强
     * @date 2023-07-08 15:14:10
     */
    public enum TimeFormat {

        YM_LINE("yyyy-MM"),

        YM_SLASH("yyyy/MM"),

        YM_NONE("yyyyMM"),

        YMD_LINE("yyyy-MM-dd"),

        YMD_SLASH("yyyy/MM/dd"),

        YMD_NONE("yyyyMMdd"),

        YMD_HMS_LINE("yyyy-MM-dd HH:mm:ss"),

        YMD_HMS_SLASH("yyyy/MM/dd HH:mm:ss"),

        YMD_HMS_NONE("yyyyMMdd HH:mm:ss"),

        YMD_HMS_OTHER("yyyyMMddHHmmss"),

        YMD_HMS_SSS_LINE("yyyy-MM-dd HH:mm:ss.SSS"),

        YMD_HMS_SSS_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),

        YMD_HMS_SSS_NONE("yyyyMMdd HH:mm:ss.SSS");

        private final transient DateTimeFormatter formatter;

        TimeFormat(String pattern) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        }
    }
}
