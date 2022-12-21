package com.pandama.top.listener.fileupload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * @description: 进度条
 * @author: 王强
 * @dateTime: 2022-12-21 10:52:49
 */
@Slf4j
@Component
public class ProgressBar {
    private static final int TOTAL = 100;
    private static final char BACK_GROUND = '-';
    private static final char FRONT_GROUND = '#';

    private StringBuilder stringBuilder;
    private Integer startPercent;

    public void init() {
        startPercent = 0;
        stringBuilder = new StringBuilder();
        Stream.generate(() -> BACK_GROUND).limit(TOTAL).forEach(stringBuilder::append);
    }

    /**
     * 打印百分比进度条
     *
     * @param bytesRead     字节读
     * @param contentLength 内容长度
     */
    public void printPercentProcess(long bytesRead, long contentLength) {
        int percent = (int) (bytesRead * 100.0 / contentLength);
        if (startPercent < percent) {
            for (int i = startPercent; i < percent; i++) {
                stringBuilder.replace(i, Math.min(TOTAL, i + 1), String.valueOf(FRONT_GROUND));
            }
            startPercent = Math.min(TOTAL, percent);
        }
        String bar = "\r" + stringBuilder;
        String percentRadio = " " + (percent) + "%";
        String content = ColorEnum.GREEN.getValue() + bar + percentRadio;
        content += bytesRead == contentLength ? "(上传成功)" : "(上传中)";
        // 输出绿色进度条
        System.out.print(content);
    }

    /**
     * 打印字节接收进度条
     *
     * @param bytesRead     字节读
     * @param contentLength 内容长度
     */
    public void printByteProcess(long bytesRead, long contentLength, UnitEnum unitEnum) {
        int percent = (int) (bytesRead * 100.0 / contentLength);
        if (startPercent < percent) {
            for (int i = startPercent; i < percent; i++) {
                stringBuilder.replace(i, Math.min(TOTAL, i + 1), String.valueOf(FRONT_GROUND));
            }
            startPercent = Math.min(TOTAL, percent);
        }
        String bar = "\r" + stringBuilder;
        String percentRadio = " " + bytesRead / unitEnum.getValue() + unitEnum.getName() + "/" + contentLength / unitEnum.getValue() + unitEnum.getName();
        String content = ColorEnum.GREEN.getValue() + bar + percentRadio;
        content += bytesRead == contentLength ? "(上传成功)" : "(上传中)";
        // 输出绿色进度条
        System.out.print(content);
    }

    /**
     * 颜色枚举
     */
    enum ColorEnum {
        /**
         * 白色
         */
        WHITE("\33[0m"),
        /**
         * 红色
         */
        RED("\33[1m\33[31m"),
        /**
         * 绿色
         */
        GREEN("\33[1m\33[32m"),
        /**
         * 黄色
         */
        YELLOW("\33[1m\33[33m"),
        /**
         * 蓝色
         */
        BLUE("\33[1m\33[34m"),
        /**
         * 粉色
         */
        PINK("\33[1m\33[35m"),
        /**
         * 青色
         */
        CYAN("\33[1m\33[36m");
        /**
         * 颜色值
         */
        private final String value;

        ColorEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 数据单位
     */
    enum UnitEnum {
        /**
         * B
         */
        B(1L, "B"),
        /**
         * KB
         */
        KB(1000L, "KB"),
        /**
         * MB
         */
        MB(1000L * 1000L, "MB"),
        /**
         * GB
         */
        GB(1000L * 1000L * 1000L, "GB");
        /**
         * 大小
         */
        private final Long value;
        /**
         * 名称
         */
        private final String name;

        UnitEnum(Long value, String name) {
            this.value = value;
            this.name = name;
        }

        public Long getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }
}
