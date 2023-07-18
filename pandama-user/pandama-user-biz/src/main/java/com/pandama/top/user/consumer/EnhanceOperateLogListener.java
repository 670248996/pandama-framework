package com.pandama.top.user.consumer;

import com.pandama.top.core.utils.BeanConvertUtils;
import com.pandama.top.logRecord.bean.LogDTO;
import com.pandama.top.rocketmq.handler.EnhanceMessageHandler;
import com.pandama.top.user.entity.SysLog;
import com.pandama.top.user.mapper.LogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 加强成员消息侦听器
 *
 * @author 王强
 * @date 2023-07-08 15:41:25
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RocketMQMessageListener(
        consumerGroup = "operate_log_consumer_group",
        topic = "operate_log_topic",
        selectorExpression = "*",
        consumeThreadNumber = 10)
public class EnhanceOperateLogListener extends EnhanceMessageHandler<LogDTO> implements RocketMQListener<LogDTO> {

    private final LogMapper logMapper;

    @Override
    protected void handleMessage(LogDTO message) throws Exception {
        // 此时这里才是最终的业务处理，代码只需要处理资源类关闭异常，其他的可以交给父类重试
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

    @Override
    protected void handleMaxRetriesExceeded(LogDTO message) {
        // 当超过指定重试次数消息时此处方法会被调用
        // 生产中可以进行回退或其他业务操作
        log.error("消息消费失败，请执行后续处理");
    }

    /**
     * 是否执行重试机制
     */
    @Override
    protected boolean isRetry() {
        return true;
    }

    @Override
    protected boolean throwException() {
        // 是否抛出异常，false搭配retry自行处理异常
        return false;
    }

    /**
     * 监听消费消息，不需要执行业务处理，委派给父类做基础操作，父类做完基础操作后会调用子类的实际处理类型
     */
    @Override
    public void onMessage(LogDTO memberMessage) {
        super.dispatchMessage(memberMessage);
    }
}
