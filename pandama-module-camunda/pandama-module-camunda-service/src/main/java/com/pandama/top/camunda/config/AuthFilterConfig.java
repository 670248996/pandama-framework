package com.pandama.top.camunda.config;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.rest.security.auth.ProcessEngineAuthenticationFilter;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.camunda.bpm.spring.boot.starter.event.PreUndeployEvent;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * 身份验证过滤器配置
 *
 * @author 王强
 * @date 2024-09-05 21:23:53
 */
@Slf4j
@Configuration
public class AuthFilterConfig implements ServletContextInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        FilterRegistration.Dynamic dynamic = servletContext.addFilter("camunda-auth", ProcessEngineAuthenticationFilter.class);
        dynamic.setAsyncSupported(true);
        dynamic.setInitParameter("authentication-provider", "org.camunda.bpm.engine.rest.security.auth.impl.HttpBasicAuthenticationProvider");
        dynamic.addMappingForUrlPatterns(null, true, "/engine-rest/**");
    }

    @EventListener
    public void onPostDeploy(PostDeployEvent event) {
        String name = event.getProcessEngine().getName();
        log.info("Camunda {}流程引擎部署成功", name);
    }

    @EventListener
    public void onPreUndeploy(PreUndeployEvent event) {
        String name = event.getProcessEngine().getName();
        log.info("Camunda {}流程引擎服务关闭", name);
    }
}
