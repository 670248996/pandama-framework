package com.pandama.top.app.consumer;

import com.pandama.top.app.message.MemberMessage;
import com.pandama.top.rocketmq.handler.EnhanceMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @description: 加强成员消息侦听器
 * @author: 王强
 * @dateTime: 2023-06-17 21:12:10
 */
@Slf4j
@Component
@RocketMQMessageListener(
        consumerGroup = "enhance_consumer_group",
        topic = "test_topic",
        selectorExpression = "*",
        consumeThreadNumber = 5)
public class EnhanceMemberMessageListener extends EnhanceMessageHandler<MemberMessage> implements RocketMQListener<MemberMessage> {

    @Override
    protected void handleMessage(MemberMessage message) throws Exception {
        // 此时这里才是最终的业务处理，代码只需要处理资源类关闭异常，其他的可以交给父类重试
        System.out.println("业务消息处理:" + message.getUsername());
    }

    @Override
    protected void handleMaxRetriesExceeded(MemberMessage message) {
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
    public void onMessage(MemberMessage memberMessage) {
        super.dispatchMessage(memberMessage);
    }
}
