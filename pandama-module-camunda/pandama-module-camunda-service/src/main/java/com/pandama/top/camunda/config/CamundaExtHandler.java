package com.pandama.top.camunda.config;

import com.pandama.top.core.global.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngineException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常统一拦截类
 *
 * @author 王强
 * @date 2023-07-08 15:32:07
 */
@Slf4j
@RestControllerAdvice
public class CamundaExtHandler {

    /**
     * 工作流处理异常
     *
     * @param e 未知异常类型
     * @return com.pandama.top.core.global.response.Response<?>
     * @author 王强
     */
    @ResponseBody
    @ExceptionHandler(value = ProcessEngineException.class)
    public Response<?> handleProcessEngineException(Exception e) {
        e.printStackTrace();
        log.info("ProcessEngineException异常: {}", e.getMessage());
        return Response.fail(e.getMessage());
    }

}
