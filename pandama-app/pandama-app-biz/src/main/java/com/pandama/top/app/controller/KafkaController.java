package com.pandama.top.app.controller;

import com.pandama.top.core.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

/**
 * 设备控制器
 *
 * @author 王强
 * @date 2023-07-08 11:55:46
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class KafkaController {

    private final KafkaTemplate<String, String> template;

    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public Response<?> generate(String topic, Integer num) {
        Stream.iterate(0, n -> n + 1).parallel().limit(num).forEach(item -> {
            template.send(topic, String.valueOf(item)).addCallback(result -> {
                log.info("发送成功: {}, result: {}", item, result);
            }, ex -> {
                log.info("发送失败: {}, ex: {}", item, ex.getMessage());
            });
        });
        return Response.success();
    }
}
