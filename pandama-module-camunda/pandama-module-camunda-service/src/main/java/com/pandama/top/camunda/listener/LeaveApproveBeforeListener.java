package com.pandama.top.camunda.listener;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

/**
 * 在侦听器之前保留 Approve
 *
 * @author 王强
 * @date 2024-09-11 14:10:12
 */
@Slf4j
@Component("leaveApproveBefore")
public class LeaveApproveBeforeListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) {
        log.info("发送短信：您有一个请假申请需要审批！");
    }
}
