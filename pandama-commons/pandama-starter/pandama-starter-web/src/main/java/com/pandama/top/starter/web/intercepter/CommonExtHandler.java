package com.pandama.top.starter.web.intercepter;

import com.pandama.top.core.global.exception.CommonException;
import com.pandama.top.core.global.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全局异常统一拦截类
 *
 * @author 王强
 * @date 2023-07-08 15:32:07
 */
@Slf4j
@RestControllerAdvice
public class CommonExtHandler {

    /**
     * 处理异常
     *
     * @param e 未知异常类型
     * @return com.pandama.top.core.global.response.Response<?>
     * @author 王强
     */
    @ExceptionHandler(value = Exception.class)
    public Response<?> handleException(Exception e) {
        e.printStackTrace();
        log.info("Exception异常: {}", e.getMessage());
        return Response.fail("服务端异常");
    }

    /**
     * 前后端参数不匹配校验（json解析异常）
     *
     * @param e json异常类型
     * @return com.pandama.top.core.global.response.Response<?>
     * @author 王强
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Response<?> validJson(HttpMessageNotReadableException e) {
        e.printStackTrace();
        log.info("Json异常: {}", e.getMessage());
        return Response.fail("非法的JSON格式");
    }

    /**
     * controller方法中，对象作为参数的校验
     *
     * @param e 对象参数异常
     * @return com.pandama.top.core.global.response.Response<?>
     * @author 王强
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<?> validEntity(MethodArgumentNotValidException e) {
        Map<String, String> collect = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        String msg = String.join(",", collect.values());
        return Response.fail(msg);
    }

    /**
     * controller方法中，表单及普通参数的校验
     *
     * @param e 表单及普通参数异常
     * @return com.pandama.top.core.global.response.Response<?>
     * @author 王强
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Response<?> validParam(ConstraintViolationException e) {
        Map<String, Object> collect = new HashMap<>(16);
        e.getConstraintViolations().forEach(c -> {
            PathImpl propertyPath = (PathImpl) c.getPropertyPath();
            NodeImpl leafNode = propertyPath.getLeafNode();
            String name = leafNode.getName();
            String value = c.getMessageTemplate();
            collect.put(name, value);
        });
        String msg = String.join(",", String.valueOf(collect.values()));
        return Response.fail(msg);
    }

    /**
     * controller方法中：1、对象接收表单数据的校验；2、对象接收表单数据并做分组验参数据的校验
     *
     * @param e 表单参数异常
     * @return com.pandama.top.core.global.response.Response<?>
     * @author 王强
     */
    @ExceptionHandler(BindException.class)
    public Response<?> exceptionHandler(BindException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        Map<String, String> collect = new HashMap<>(16);
        allErrors.forEach(error -> {
            FieldError fieldError = (FieldError) error;
            collect.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        String msg = String.join(",", collect.values());
        return Response.fail(msg);
    }

    /**
     * 处理自定义异常类
     *
     * @param e 自定义异常
     * @return com.pandama.top.core.global.response.Response<?>
     * @author 王强
     */
    @ExceptionHandler(value = CommonException.class)
    public Response<?> handleMyException(CommonException e) {
        e.printStackTrace();
        log.info("CommonException异常: {}", e.getMsg());
        return Response.fail(e.getMsg());
    }

}
