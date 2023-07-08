package com.pandama.top.core.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;
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
    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = TimeFormat.LONG_DATE_PATTERN_LINE.formatter;

    /**
     * 无参数的构造函数,防止被实例化
     */
    private DateUtils() {}

    /**
     * String 转化为 LocalDateTime
     *
     * @param timeStr 被转化的字符串
     * @return java.time.LocalDateTime
     * @author 王强
     */
    public static LocalDateTime parseTime(String timeStr) {
        return LocalDateTime.parse(timeStr, DEFAULT_DATETIME_FORMATTER);

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
        return DEFAULT_DATETIME_FORMATTER.format(time);

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
     * 获取当前时间
     *
     * @return java.lang.String
     * @author 王强
     */
    public static String getCurrentDateTime() {
        return DEFAULT_DATETIME_FORMATTER.format(LocalDateTime.now());
    }

    /**
     * 获取当前时间
     *
     * @return java.lang.String
     * @author 王强
     */
    public static String now() {
        return DEFAULT_DATETIME_FORMATTER.format(LocalDateTime.now());
    }

    /**
     * 获取当前时间
     *
     * @param timeFormat 时间格式
     * @return java.lang.String
     * @author 王强
     */
    public static String getCurrentDateTime(TimeFormat timeFormat) {
        return timeFormat.formatter.format(LocalDateTime.now());
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
        // 短时间格式 年月日
        /**
         * 时间格式：yyyy-MM-dd
         */
        SHORT_DATE_PATTERN_LINE("yyyy-MM-dd"),
        /**
         * 时间格式：yyyy/MM/dd
         */
        SHORT_DATE_PATTERN_SLASH("yyyy/MM/dd"),
        /**
         * 时间格式：yyyy\\MM\\dd
         */
        SHORT_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd"),
        /**
         * 时间格式：yyyyMMdd
         */
        SHORT_DATE_PATTERN_NONE("yyyyMMdd"),

        // 长时间格式 年月日时分秒
        /**
         * 时间格式：yyyy-MM-dd HH:mm:ss
         */
        LONG_DATE_PATTERN_LINE("yyyy-MM-dd HH:mm:ss"),

        /**
         * 时间格式：yyyy/MM/dd HH:mm:ss
         */
        LONG_DATE_PATTERN_SLASH("yyyy/MM/dd HH:mm:ss"),
        /**
         * 时间格式：yyyy\\MM\\dd HH:mm:ss
         */
        LONG_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss"),
        /**
         * 时间格式：yyyyMMdd HH:mm:ss
         */
        LONG_DATE_PATTERN_NONE("yyyyMMdd HH:mm:ss"),
        /**
         * 时间格式：yyyyMMddHHmmss
         */
        LONG_DATE_PATTERN_NONE_OTHER("yyyyMMddHHmmss"),

        // 长时间格式 年月日时分秒 带毫秒
        LONG_DATE_PATTERN_WITH_MILSEC_LINE("yyyy-MM-dd HH:mm:ss.SSS"),

        LONG_DATE_PATTERN_WITH_MILSEC_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),

        LONG_DATE_PATTERN_WITH_MILSEC_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss.SSS"),

        LONG_DATE_PATTERN_WITH_MILSEC_NONE("yyyyMMdd HH:mm:ss.SSS");

        private final transient DateTimeFormatter formatter;

        TimeFormat(String pattern) {
            formatter = DateTimeFormatter.ofPattern(pattern);

        }
    }
}
