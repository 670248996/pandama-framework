package com.pandama.top.app.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CustomerAspect {


    /**
     * @param pjp 切点信息
     * @description: 环绕通知
     * @author: 王强
     * @date: 2022-09-02 16:29:36
     * @return: @return {@code Object }
     * @version: 1.0
     */
    @Pointcut("execution(public * com.pandama.top.app.service.*.*(..))")
    private void signAop() {

    }

    @Before("signAop()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        System.out.println("before");
    }
}
