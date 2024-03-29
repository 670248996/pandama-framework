package com.pandama.top.logRecord.configuration;

import com.pandama.top.logRecord.function.CustomFunctionObjectDiff;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 日志记录属性
 *
 * @author 王强
 * @date 2023-07-08 15:21:39
 */
@Data
@ConfigurationProperties(prefix = "log-record")
public class LogRecordProperties {

    private ThreadPoolProperties threadPool = new ThreadPoolProperties();

    private String diffMsgFormat = CustomFunctionObjectDiff.DEFAULT_DIFF_MSG_FORMAT;

    private String addMsgFormat = CustomFunctionObjectDiff.DEFAULT_ADD_MSG_FORMAT;

    private String msgFormat = CustomFunctionObjectDiff.DEFAULT_MSG_FORMAT;

    private String msgSeparator = " ";

    /**
     * 线程池属性
     *
     * @author 王强
     * @date 2023-07-08 15:21:46
     */
    @Data
    public static class ThreadPoolProperties {

        private int poolSize = 4;

        private boolean enabled = false;
    }

}
