package com.pandama.top.camunda.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程实例服务
 *
 * @author 王强
 * @date 2024-09-06 21:56:23
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CamundaInstanceService {
    private final RuntimeService runtimeService;

    public List<ProcessInstance> getList() {
        return runtimeService.createProcessInstanceQuery().list();
    }

    public void suspend(String instanceId) {
        runtimeService.suspendProcessInstanceById(instanceId);
    }

    public void activate(String instanceId) {
        runtimeService.activateProcessInstanceById(instanceId);
    }

    public void delete(String definitionId) {
        runtimeService.deleteProcessInstance(definitionId, "手动删除");
    }
}
