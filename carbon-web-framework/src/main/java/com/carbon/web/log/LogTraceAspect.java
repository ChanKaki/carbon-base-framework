package com.carbon.web.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.UUID;

/**
 * 类<code>LogTraceAspect</code>说明：
 *
 * @author kaki
 * @since 21/8/2023
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class LogTraceAspect {

    /**
     * 与 logback-spring.xml 中的变量一致
     */
    private static final String TRACE_ID = "traceId";

    /**
     * 定义切点
     */
    @Pointcut(
            "(@annotation(org.springframework.web.bind.annotation.RequestMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.PatchMapping)) &&" +
            "execution(* com.carbon..*(..))")
    public void logTraceAspect(){}

    /**
     * 环绕通知
     */
    @Around(value = "logTraceAspect()")
    public Object webLogAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 方法执行前加上链路号
        String traceId = UUID.randomUUID().toString().replaceAll("-", "");
        MDC.put(TRACE_ID, traceId);
        Object proceed = joinPoint.proceed();
        MDC.remove(TRACE_ID);
        return proceed;
    }

}
