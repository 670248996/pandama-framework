package com.pandama.top.rocketmq.template;

import com.alibaba.fastjson.JSONObject;
import com.pandama.top.rocketmq.configuration.RocketEnhanceProperties;
import com.pandama.top.core.pojo.BaseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.StringUtils;

/**
 * 火箭mqenhance模板
 *
 * @author 王强
 * @date 2023-07-08 15:30:17
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RocketMQEnhanceTemplate {

    private final RocketMQTemplate template;

    private final RocketEnhanceProperties properties;

    public RocketMQTemplate getTemplate() {
        return template;
    }

    /**
     * 根据系统上下文自动构建隔离后的topic
     *
     * @param topic 主题
     * @param tag   标签
     * @return java.lang.String
     * @author 王强
     */
    public String buildDestination(String topic, String tag) {
        topic = reBuildTopic(topic);
        return topic + ":" + tag;
    }

    /**
     * 根据环境重新隔离topic
     *
     * @param topic 原始topic
     * @return java.lang.String
     * @author 王强
     */
    private String reBuildTopic(String topic) {
        if (properties.isEnabledIsolation() && StringUtils.hasText(properties.getEnvironment())) {
            return topic + "_" + properties.getEnvironment();
        }
        return topic;
    }

    /**
     * 发送同步消息
     *
     * @param topic   主题
     * @param tag     标签
     * @param message 消息
     * @return org.apache.rocketmq.client.producer.SendResult
     * @author 王强
     */
    public <T extends BaseMessage> SendResult send(String topic, String tag, T message) {
        // 注意分隔符
        return send(buildDestination(topic, tag), message);
    }

    /**
     * 发送
     *
     * @param destination 目地
     * @param message     消息
     * @return org.apache.rocketmq.client.producer.SendResult
     * @author 王强
     */
    public <T extends BaseMessage> SendResult send(String destination, T message) {
        // 设置业务键，此处根据公共的参数进行处理
        // 更多的其它基础业务处理...
        Message<T> sendMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, message.getKey()).build();
        SendResult sendResult = template.syncSend(destination, sendMessage);
        // 此处为了方便查看给日志转了json，根据选择选择日志记录方式，例如ELK采集
        log.info("[{}]同步消息[{}]发送结果[{}]", destination, JSONObject.toJSON(message), JSONObject.toJSON(sendResult));
        return sendResult;
    }

    /**
     * 发送延迟消息
     *
     * @param topic      主题
     * @param tag        标签
     * @param message    消息
     * @param delayLevel 延迟水平
     * @return org.apache.rocketmq.client.producer.SendResult
     * @author 王强
     */
    public <T extends BaseMessage> SendResult send(String topic, String tag, T message, int delayLevel) {
        return send(buildDestination(topic, tag), message, delayLevel);
    }

    /**
     * 发送
     *
     * @param destination 目地
     * @param message     消息
     * @param delayLevel  延迟水平
     * @return org.apache.rocketmq.client.producer.SendResult
     * @author 王强
     */
    public <T extends BaseMessage> SendResult send(String destination, T message, int delayLevel) {
        Message<T> sendMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, message.getKey()).build();
        SendResult sendResult = template.syncSend(destination, sendMessage, 3000, delayLevel);
        log.info("[{}]延迟等级[{}]消息[{}]发送结果[{}]", destination, delayLevel, JSONObject.toJSON(message), JSONObject.toJSON(sendResult));
        return sendResult;
    }
}
