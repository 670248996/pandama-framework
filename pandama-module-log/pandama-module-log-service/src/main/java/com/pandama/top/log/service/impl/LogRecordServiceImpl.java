package com.pandama.top.log.service.impl;

import com.pandama.top.core.utils.BeanConvertUtils;
import com.pandama.top.logRecord.bean.LogDTO;
import com.pandama.top.logRecord.service.LogRecordService;
import com.pandama.top.log.entity.SysLog;
import com.pandama.top.log.mapper.LogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 日志记录服务impl
 *
 * @author 王强
 * @date 2023-07-08 15:53:52
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LogRecordServiceImpl implements LogRecordService {

    private final LogMapper logMapper;

    @Override
    public void createLog(LogDTO message) {
        // 处理消息的逻辑
        log.info("保存操作日志: " + message);
        SysLog sysLog = BeanConvertUtils.convert(message, SysLog::new, (s, t) -> {
            t.setModule(s.getBizType());
            t.setEvent(s.getBizEvent());
            t.setMsg(s.getMsg());
            t.setExtra(s.getExtra());
            t.setType(Integer.valueOf(s.getTag()));
            t.setCreateTime(LocalDateTime.now());
            t.setCreateUserId(s.getOperatorId());
            t.setCreateUserName(s.getOperatorName());
            t.setCreateUserCode(s.getOperatorCode());
        }).orElse(new SysLog());
        logMapper.insert(sysLog);
    }
}
