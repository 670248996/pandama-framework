package com.pandama.top.camunda.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 流程定义服务
 *
 * @author 王强
 * @date 2024-09-06 21:56:23
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CamundaProcessService {
    private final RepositoryService repositoryService;
    private final RuntimeService runtimeService;

    public List<ProcessDefinition> getList() {
        return repositoryService.createProcessDefinitionQuery().list();
    }

    public ProcessInstance start(String definitionId, String businessId, Map<String, Object> variables) {
        return runtimeService.startProcessInstanceById(definitionId, businessId, variables);
    }

    public void suspend(String definitionId) {
        repositoryService.suspendProcessDefinitionById(definitionId);
    }

    public void activate(String definitionId) {
        repositoryService.activateProcessDefinitionById(definitionId);
    }

    public void delete(String definitionId) {
        repositoryService.deleteProcessDefinition(definitionId);
    }
}
