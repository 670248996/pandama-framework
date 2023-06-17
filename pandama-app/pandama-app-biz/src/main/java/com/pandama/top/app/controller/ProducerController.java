package com.pandama.top.app.controller;

import com.pandama.top.app.message.MemberMessage;
import com.pandama.top.rocketmq.template.RocketMQEnhanceTemplate;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/producer")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProducerController {

    /**
     * 注入增强后的模板，可以自动实现环境隔离，日志记录
     */
    @Setter(onMethod_ = @Autowired)
    private RocketMQEnhanceTemplate enhanceTemplate;

    private static final String TOPIC = "rocket_enhance";
    private static final String TAG = "member";

    /**
     * 发送实体消息
     */
    @GetMapping("/member")
    public SendResult member() {
        String key = UUID.randomUUID().toString();
        MemberMessage message = new MemberMessage();
        // 设置业务key
        message.setKey(key);
        // 设置消息来源，便于查询
        message.setSource("MEMBER");
        // 业务消息内容
        message.setUsername("Java日知录");
        message.setBirthday(LocalDate.now());
        return enhanceTemplate.send(TOPIC, TAG, message);
    }

}
