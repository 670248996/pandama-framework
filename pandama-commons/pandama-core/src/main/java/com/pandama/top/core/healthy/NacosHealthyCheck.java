package com.pandama.top.core.healthy;

import com.alibaba.cloud.nacos.registry.NacosRegistration;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * NACOS 健康检查
 *
 * @author 王强
 * @date 2024-09-14 15:09:17
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class NacosHealthyCheck {
    private final NacosServiceRegistry nacosServiceRegistry;
    private final NacosRegistration nacosRegistration;

    @PostConstruct
    public void check() {
        try {
            nacosServiceRegistry.register(nacosRegistration);
        } catch (Exception e) {
            e.printStackTrace();
            Runtime.getRuntime().halt(1);
        }
    }
}
