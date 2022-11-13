package com.pandama.top.logRecord.service;

import com.pandama.top.logRecord.bean.LogDTO;

/**
 * @description: 日志记录服务
 * @author: 王强
 * @dateTime: 2022-09-02 17:31:49
 */
public interface ILogRecordService {

    /**
     * 自定义日志监听
     * @param logDTO 日志传输实体
     */
    void createLog(LogDTO logDTO);

}
