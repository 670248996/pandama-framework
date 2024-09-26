package com.pandama.top.camunda.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 在侦听器之前保留 Approve
 *
 * @author 王强
 * @date 2024-09-11 14:10:12
 */
@Slf4j
@Component("sendMessageNotify")
public class SendMessageNotifyListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        Map<String, Object> variables = delegateTask.getVariables();
        log.info("发送短信：{}", JSON.toJSONString(variables));
    }
}
