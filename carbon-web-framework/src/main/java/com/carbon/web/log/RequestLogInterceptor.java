package com.carbon.web.log;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author kaki
 * @date 2022/12/3
 */
@Aspect
@Component
@Slf4j
@Order(2)
public class RequestLogInterceptor {

    @Pointcut("execution(* com.carbon.controller..*.*(..))")
    public void requestLogAspect() {
    }

    @Before(value = "requestLogAspect()")
    public void methodBefore(JoinPoint joinPoint) {
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            //打印请求内容,有些入参不支持json格式
            log.debug("[{}]{}，请求数据:[{}]",
                    request.getMethod(), request.getRequestURI(), Optional.ofNullable(joinPoint.getArgs()).map(args -> args.toString()).orElse(null));
        } catch (Exception e) {
            log.warn("controller 层打印日志出错");
            e.printStackTrace();
        }
    }
}
