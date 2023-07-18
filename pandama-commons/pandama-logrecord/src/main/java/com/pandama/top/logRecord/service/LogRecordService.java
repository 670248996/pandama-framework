package com.pandama.top.logRecord.service;

import com.pandama.top.logRecord.bean.LogDTO;

/**
 * 日志记录服务
 *
 * @author 王强
 * @date 2023-07-08 15:24:23
 */
public interface LogRecordService {

    /**
     * 自定义日志监听
     *
     * @param logDTO 日志传输实体
     * @author 王强
     */
    void createLog(LogDTO logDTO);

}
