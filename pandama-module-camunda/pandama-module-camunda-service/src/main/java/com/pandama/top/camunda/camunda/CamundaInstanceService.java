package com.pandama.top.camunda.camunda;

import camundajar.impl.com.google.gson.Gson;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.pandama.top.core.pojo.dto.PageDTO;
import com.pandama.top.core.pojo.vo.PageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<JSONObject> list() {
        List<ProcessInstance> instanceList = runtimeService.createProcessInstanceQuery().list();
        return instanceList.stream().map(p -> JSON.parseObject(new Gson().toJson(p))).collect(Collectors.toList());
    }

    public PageVO<JSONObject> page(PageDTO dto) {
        long count = runtimeService.createProcessInstanceQuery().count();
        List<ProcessInstance> instanceList = runtimeService.createProcessInstanceQuery().listPage(dto.getFirstSize(), dto.getLastSize());
        List<JSONObject> collect = instanceList.stream()
                .map(p -> JSON.parseObject(new Gson().toJson(p))).collect(Collectors.toList());
        return new PageVO<>(count, dto.getSize().longValue(), dto.getCurrent().longValue(), collect);
    }

    public void suspend(String instanceId) {
        runtimeService.suspendProcessInstanceById(instanceId);
    }

    public void activate(String instanceId) {
        runtimeService.activateProcessInstanceById(instanceId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(String instanceId) {
        runtimeService.suspendProcessInstanceById(instanceId);
        runtimeService.deleteProcessInstance(instanceId, "手动删除");
    }
}
