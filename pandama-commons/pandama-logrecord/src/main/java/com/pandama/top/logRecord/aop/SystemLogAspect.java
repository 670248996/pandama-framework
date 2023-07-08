package com.pandama.top.logRecord.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pandama.top.core.utils.UserInfoUtils;
import com.pandama.top.logRecord.annotation.OperationLog;
import com.pandama.top.logRecord.bean.LogDTO;
import com.pandama.top.logRecord.context.LogRecordContext;
import com.pandama.top.logRecord.function.CustomFunctionRegistrar;
import com.pandama.top.logRecord.service.LogRecordService;
import com.pandama.top.logRecord.thread.LogRecordThreadPool;
import com.pandama.top.starter.web.utils.IpAddressUtils;
import com.pandama.top.starter.web.utils.WebContextUtils;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

/**
 * 系统日志方面
 *
 * @author 王强
 * @date 2023-07-08 15:20:42
 */
@Aspect
@Component
@Slf4j
public class SystemLogAspect {

    /**
     * 日志记录线程池
     */
    private final LogRecordThreadPool logRecordThreadPool;

    /**
     * 操作日志服务
     */
    private final LogRecordService iOperationLogService;

    /**
     * Spel解析器
     */
    private final SpelExpressionParser parser = new SpelExpressionParser();

    /**
     * 参数名发现者
     */
    private final DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();

    public SystemLogAspect(LogRecordThreadPool logRecordThreadPool,
                           LogRecordService iOperationLogService) {
        this.logRecordThreadPool = logRecordThreadPool;
        this.iOperationLogService = iOperationLogService;
    }

    @Around("@annotation(com.pandama.top.logRecord.annotation.OperationLog) || @annotation(com.pandama.top.logRecord.annotation.OperationLogs)")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        // 客户端信息
        UserAgent userAgent = UserAgent.parseUserAgentString(WebContextUtils.getRequest().getHeader("User-Agent"));
        String ipAddress = IpAddressUtils.getIpAddress(WebContextUtils.getRequest());
        // 定义返回结果
        Object result;
        // 定义@OperationLog注解数组
        OperationLog[] annotations;
        // 定义LogDTO集合，用于存放注解解析后的LogDTO对象
        List<LogDTO> logDTOList = new ArrayList<>();
        // 定义有序的Map，用于存放注解解析后的LogDTO对象
        Map<OperationLog, LogDTO> logDtoMap = new LinkedHashMap<>();
        // 耗时监听对象
        StopWatch stopWatch = null;
        // 执行耗时
        Long executionTime = null;
        // 注解解析：若解析失败直接不执行日志切面逻辑
        try {
            // 获取切面方法
            Method method = getMethod(pjp);
            // 获取方法上的@OperationLog注解信息
            annotations = method.getAnnotationsByType(OperationLog.class);
        } catch (Throwable throwable) {
            // 若发生异常，则直接返回切面方法执行结果
            return pjp.proceed();
        }
        // 下方是日志切面逻辑
        try {
            // 方法执行前日志切面
            try {
                // 遍历注解集合
                for (OperationLog annotation : annotations) {
                    // 执行方法前解析切面逻辑
                    if (annotation.executeBeforeFunc()) {
                        // 解析注解中定义的Spel表达式，返回LogDTO对象
                        LogDTO logDTO = resolveExpress(annotation, pjp);
                        if (logDTO != null) {
                            // 若解析后的logDto数据不为空，则将logDto放入Map中
                            logDtoMap.put(annotation, logDTO);
                        }
                    }
                }
                // 初始化耗时监听对象
                stopWatch = new StopWatch();
                // 开启计时
                stopWatch.start();
            } catch (Throwable throwableBeforeFunc) {
                log.error("OperationLogAspect doAround before function, error:", throwableBeforeFunc);
            }
            // 原方法执行
            result = pjp.proceed();
            // 方法成功执行后日志切面
            try {
                if (stopWatch != null) {
                    stopWatch.stop();
                    executionTime = stopWatch.getTotalTimeMillis();
                }
                // 在LogRecordContext中写入执行后信息，将切面方法执行的返回结果写入日志记录上下文中
                LogRecordContext.putVariables(LogRecordContext.CONTEXT_KEY_NAME_RETURN, result);
                // 遍历注解集合
                for (OperationLog annotation : annotations) {
                    // 执行方法后解析切面逻辑
                    if (!annotation.executeBeforeFunc()) {
                        // 解析注解中定义的Spel表达式，返回LogDTO对象
                        LogDTO logDTO = resolveExpress(annotation, pjp);
                        if (logDTO != null) {
                            // 若解析后的logDTO数据不为空，则将logDTO放入Map中
                            logDtoMap.put(annotation, logDTO);
                        }
                    }
                }
                // 写入成功执行后日志
                logDTOList = new ArrayList<>(logDtoMap.values());
                // 遍历logDtoMap
                logDtoMap.forEach((annotation, logDTO) -> {
                    // 若自定义成功失败，则logDTO.getSuccess非null
                    if (logDTO.getSuccess() == null) {
                        logDTO.setSuccess(true);
                    }
                    // 若需要记录返回值，则logDTO.setReturnStr切面方法返回值的JSONString
                    if (annotation.recordReturnValue()) {
                        logDTO.setReturnStr(JSON.toJSONString(result));
                    }
                });
            } catch (Throwable throwableAfterFuncSuccess) {
                log.error("OperationLogAspect doAround after function success, error:", throwableAfterFuncSuccess);
            }
        }
        // 原方法执行异常
        catch (Throwable throwable) {
            // 方法异常执行后日志切面
            try {
                // 记录日志切面执行耗时
                if (stopWatch != null) {
                    // 结束计时
                    stopWatch.stop();
                    // 获取执行耗时
                    executionTime = stopWatch.getTotalTimeMillis();
                }
                // 在LogRecordContext中写入执行后信息，将切面方法执行的异常信息写入日志记录上下文中
                LogRecordContext.putVariables(LogRecordContext.CONTEXT_KEY_NAME_ERROR_MSG, throwable.getMessage());
                for (OperationLog annotation : annotations) {
                    // 执行方法后解析切面逻辑
                    if (!annotation.executeBeforeFunc()) {
                        // 解析注解中定义的Spel表达式，返回LogDTO对象
                        LogDTO logDTO = resolveExpress(annotation, pjp);
                        if (logDTO != null) {
                            // 若解析后的logDTO数据不为空，则将logDTO放入Map中
                            logDtoMap.put(annotation, logDTO);
                        }
                    }
                }
                // 写入异常执行后日志
                logDTOList = new ArrayList<>(logDtoMap.values());
                // 遍历日志信息LogDTO集合
                logDTOList.forEach(logDTO -> {
                    // 设置执行结果为false，
                    logDTO.setSuccess(false);
                    // 写入执行异常信息
                    logDTO.setException(throwable.getMessage());
                });
            } catch (Throwable throwableAfterFuncFailure) {
                log.error("OperationLogAspect doAround after function failure, error:", throwableAfterFuncFailure);
            }
            // 抛出原方法异常
            throw throwable;
        } finally {
            try {
                // 提交日志至主线程或线程池
                Long finalExecutionTime = executionTime;
                Consumer<LogDTO> createLogFunction = logDTO -> {
                    try {
                        // 记录日志切面执行时间
                        logDTO.setExecutionTime(finalExecutionTime);
                        // 发送日志本地监听
                        if (iOperationLogService != null) {
                            logDTO.setIpAddress(ipAddress);
                            logDTO.setOs(userAgent.getOperatingSystem().getName());
                            logDTO.setBrowser(userAgent.getBrowser().getName());
                            iOperationLogService.createLog(logDTO);
                        }
                    } catch (Throwable throwable) {
                        log.error("OperationLogAspect send logDTO error", throwable);
                    }
                };
                // 判断是否使用日志记录线程池
                if (logRecordThreadPool != null) {
                    // 使用线程池异步处理日志
                    logDTOList.forEach(logDTO ->
                            logRecordThreadPool.getLogRecordPoolExecutor().submit(() ->
                                    createLogFunction.accept(logDTO)));
                } else {
                    // 同步处理日志
                    logDTOList.forEach(createLogFunction);
                }
                // 清除Context：每次方法执行一次
                LogRecordContext.clearContext();
            } catch (Throwable throwableFinal) {
                log.error("OperationLogAspect doAround final error", throwableFinal);
            }
        }
        return result;
    }

    /**
     * 解析注解中定义的spel表达式
     *
     * @param annotation 注释
     * @param joinPoint  连接点
     * @return com.pandama.top.logRecord.bean.LogDTO
     * @author 王强
     */
    private LogDTO resolveExpress(OperationLog annotation, JoinPoint joinPoint) {
        // 定义LogDTO对象，Spel解析后的对象
        LogDTO logDTO = null;
        // 业务ID，SpEL表达式
        String bizIdSpel = annotation.bizId();
        // 业务名称，SpEL表达式
        String bizNameSpel = annotation.bizName();
        // 业务类型，SpEL表达式
        String bizTypeSpel = annotation.bizType();
        // 业务事件，SpEL表达式
        String bizEventSpel = annotation.bizEvent();
        // 日志标签，SpEL表达式
        String tagSpel = annotation.tag();
        // 日志内容，SpEL表达式
        String msgSpel = annotation.msg();
        // 额外信息，SpEL表达式
        String extraSpel = annotation.extra();
        // 操作人ID，SpEL表达式
        String operatorIdSpel = annotation.operatorId();
        // 日志记录条件，SpEL表达式
        String conditionSpel = annotation.condition();
        // 执行是否成功，SpEL表达式
        String successSpel = annotation.success();

        // 业务ID，SpEL解析结果，默认为SpEL表达式字符串
        String bizId = bizIdSpel;
        // 业务名称，SpEL解析结果，默认为SpEL表达式字符串
        String bizName = bizNameSpel;
        // 业务类型，SpEL解析结果，默认为SpEL表达式字符串
        String bizType = bizTypeSpel;
        // 业务事件，SpEL解析结果，默认为SpEL表达式字符串
        String bizEvent = bizEventSpel;
        // 日志标签，SpEL解析结果，默认为SpEL表达式字符串
        String tag = tagSpel;
        // 日志内容，SpEL解析结果，默认为SpEL表达式字符串
        String msg = msgSpel;
        // 额外信息，SpEL解析结果，默认为SpEL表达式字符串
        String extra = extraSpel;
        // 操作人ID，SpEL解析结果，默认为SpEL表达式字符串
        String operatorId = operatorIdSpel;
        // 执行是否成功，SpEL解析结果，默认为null
        Boolean functionExecuteSuccess = null;

        try {
            // 获取切面的方法入参
            Object[] arguments = joinPoint.getArgs();
            // 获取切面方法
            Method method = getMethod(joinPoint);
            // 获取切面方法的参数名
            String[] params = discoverer.getParameterNames(method);
            // 获取日志记录上下文
            StandardEvaluationContext context = LogRecordContext.getContext();
            // 注册自定义函数
            CustomFunctionRegistrar.register(context);
            if (params != null) {
                // 编辑方法参数，将参数放入日志记录上下文中
                for (int len = 0; len < params.length; len++) {
                    context.setVariable(params[len], arguments[len]);
                }
            }

            // condition 处理：SpEL解析 必须符合表达式
            if (StringUtils.isNotBlank(conditionSpel)) {
                Expression conditionExpression = parser.parseExpression(conditionSpel);
                boolean passed = Boolean.TRUE.equals(conditionExpression.getValue(context, Boolean.class));
                if (!passed) {
                    return null;
                }
            }

            // success 处理：SpEL解析 必须符合表达式
            if (StringUtils.isNotBlank(successSpel)) {
                Expression successExpression = parser.parseExpression(successSpel);
                functionExecuteSuccess = Boolean.TRUE.equals(successExpression.getValue(context, Boolean.class));
            }

            // bizId 处理：SpEL解析 必须符合表达式
            if (StringUtils.isNotBlank(bizIdSpel)) {
                Expression bizIdExpression = parser.parseExpression(bizIdSpel);
                bizId = bizIdExpression.getValue(context, String.class);
            }

            // bizName 处理：SpEL解析 必须符合表达式
            if (StringUtils.isNotBlank(bizNameSpel)) {
                Expression bizNameExpression = parser.parseExpression(bizNameSpel);
                bizName = bizNameExpression.getValue(context, String.class);
            }

            // bizType 处理：SpEL解析 必须符合表达式
            if (StringUtils.isNotBlank(bizTypeSpel)) {
                Expression bizTypeExpression = parser.parseExpression(bizTypeSpel);
                bizType = bizTypeExpression.getValue(context, String.class);
            }

            // bizEvent 处理：SpEL解析 必须符合表达式
            if (StringUtils.isNotBlank(bizEventSpel)) {
                Expression bizEventExpression = parser.parseExpression(bizEventSpel);
                bizEvent = bizEventExpression.getValue(context, String.class);
            }

            // tag 处理：SpEL解析 必须符合表达式
            if (StringUtils.isNotBlank(tagSpel)) {
                Expression tagExpression = parser.parseExpression(tagSpel);
                tag = tagExpression.getValue(context, String.class);
            }

            // msg 处理：SpEL解析 必须符合表达式 若为实体则JSON序列化实体
            if (StringUtils.isNotBlank(msgSpel)) {
                Expression msgExpression = parser.parseExpression(msgSpel);
                Object msgObj = msgExpression.getValue(context, Object.class);
                msg = msgObj instanceof String
                        ? (String) msgObj : JSON.toJSONString(msgObj, SerializerFeature.WriteMapNullValue);
            }

            // extra 处理：SpEL解析 必须符合表达式 若为实体则JSON序列化实体
            if (StringUtils.isNotBlank(extraSpel)) {
                Expression extraExpression = parser.parseExpression(extraSpel);
                Object extraObj = extraExpression.getValue(context, Object.class);
                extra = extraObj instanceof String
                        ? (String) extraObj : JSON.toJSONString(extraObj, SerializerFeature.WriteMapNullValue);
            }

            // 封装日志对象 logDTO
            logDTO = new LogDTO();
            logDTO.setBizId(bizId);
            logDTO.setBizName(bizName);
            logDTO.setBizType(bizType);
            logDTO.setBizEvent(bizEvent);
            logDTO.setTag(tag);
            logDTO.setOperateDate(new Date());
            logDTO.setMsg(msg);
            logDTO.setExtra(extra);
            logDTO.setOperatorId(UserInfoUtils.getUserId());
            logDTO.setOperatorCode(UserInfoUtils.getUserInfo().getUsername());
            logDTO.setOperatorName(UserInfoUtils.getUserInfo().getRealName());
            logDTO.setSuccess(functionExecuteSuccess);
            logDTO.setDiffDTOList(LogRecordContext.getDiffDTOList());
        } catch (Exception e) {
            log.error("OperationLogAspect resolveExpress error", e);
        } finally {
            // 清除Diff实体列表：每次注解执行一次
            LogRecordContext.clearDiffDTOList();
        }
        return logDTO;
    }

    /**
     * 获取切点方法
     *
     * @param joinPoint 切点
     * @return java.lang.reflect.Method
     * @author 王强
     */
    private Method getMethod(JoinPoint joinPoint) {
        Method method = null;
        try {
            Signature signature = joinPoint.getSignature();
            MethodSignature ms = (MethodSignature) signature;
            Object target = joinPoint.getTarget();
            method = target.getClass().getMethod(ms.getName(), ms.getParameterTypes());
        } catch (NoSuchMethodException e) {
            log.error("OperationLogAspect getMethod error", e);
        }
        return method;
    }
}
