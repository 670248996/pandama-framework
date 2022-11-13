package com.pandama.top.logRecord.exception;

/**
 * @description: 日志记录异常
 * @author: 王强
 * @dateTime: 2022-09-02 17:49:08
 */
public class LogRecordException extends RuntimeException {

    public LogRecordException(String message, Throwable cause) {
        super(message, cause);
    }
}
