package com.pandama.top.app.controller;

import com.pandama.top.app.message.MemberMessage;
import com.pandama.top.rocketmq.template.RocketMQEnhanceTemplate;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @description: producer控制器
 * @author: 王强
 * @dateTime: 2023-06-16 16:46:33
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/rocket")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RocketController {

    /**
     * 注入增强后的模板，可以自动实现环境隔离，日志记录
     */
    @Setter(onMethod_ = @Autowired)
    private RocketMQEnhanceTemplate rocketTemplate;

    private static final String TOPIC = "test_topic";
    private static final String TAG = "test_tag";

    /**
     * 发送实体消息
     */
    @GetMapping("/send")
    public SendResult send() {
        String key = UUID.randomUUID().toString();
        MemberMessage message = new MemberMessage();
        // 设置业务key
        message.setKey(key);
        // 设置消息来源，便于查询
        message.setSource("MEMBER");
        // 业务消息内容
        message.setUsername("Java日知录");
        message.setBirthday(LocalDate.now());
        return rocketTemplate.send(TOPIC, TAG, message);
    }

}
