package com.pandama.top.camunda.camunda;

import com.alibaba.fastjson2.JSONObject;
import com.pandama.top.core.pojo.dto.PageDTO;
import com.pandama.top.core.pojo.vo.PageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public List<JSONObject> list() {
        List<ProcessDefinition> definitionList = repositoryService.createProcessDefinitionQuery()
                .latestVersion().orderByDeploymentTime().desc().list();
        return definitionList.stream()
                .collect(Collectors.toMap(ProcessDefinition::getKey, Function.identity(), (k1, k2) -> k1)).values().stream()
                .map(p -> {
                    JSONObject json = new JSONObject();
                    json.put("id", p.getId());
                    json.put("name", p.getName());
                    return json;
                }).collect(Collectors.toList());
    }

    public PageVO<JSONObject> page(PageDTO dto) {
        long count = repositoryService.createProcessDefinitionQuery().latestVersion().count();
        List<ProcessDefinition> definitionList = repositoryService.createProcessDefinitionQuery()
                .latestVersion().orderByDeploymentTime().desc().listPage(dto.getFirstSize(), dto.getLastSize());
        List<JSONObject> collect = definitionList.stream()
                .map(p -> {
                    JSONObject json = new JSONObject();
                    json.put("id", p.getId());
                    json.put("name", p.getName());
                    return json;
                }).collect(Collectors.toList());
        return new PageVO<>(count, dto.getSize().longValue(), dto.getCurrent().longValue(), collect);
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


    @Transactional(rollbackFor = Exception.class)
    public void delete(String definitionId) {
        repositoryService.suspendProcessDefinitionById(definitionId);
        List<ProcessInstance> instanceList = runtimeService.createProcessInstanceQuery().processDefinitionId(definitionId).list();
        if (CollectionUtils.isNotEmpty(instanceList)) {
            throw new RuntimeException("流程存在流转中的实例，无法删除！");
        }
        repositoryService.deleteProcessDefinition(definitionId, true);
    }
}
