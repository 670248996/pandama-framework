package com.pandama.top.app.service.impl;

import com.pandama.top.logRecord.bean.LogDTO;
import com.pandama.top.logRecord.service.LogRecordService;
import com.pandama.top.rocketmq.template.RocketMQEnhanceTemplate;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 日志记录服务impl
 *
 * @author 王强
 * @date 2023-07-08 15:11:23
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LogRecordServiceImpl implements LogRecordService {

    /**
     * 注入增强后的模板，可以自动实现环境隔离，日志记录
     */
    @Setter(onMethod_ = @Autowired)
    private RocketMQEnhanceTemplate rocketTemplate;

    @Override
    public void createLog(LogDTO logDTO) {
        LogDTO message = new LogDTO();
        // 设置业务key
        message.setKey(UUID.randomUUID().toString());
        // 设置消息来源，便于查询
        message.setSource("APP");
        log.info("发送操作日志到MQ: {}", message);
        rocketTemplate.send("operate_log_topic", message);
    }
}
