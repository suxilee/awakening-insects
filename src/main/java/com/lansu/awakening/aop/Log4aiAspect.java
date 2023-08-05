package com.lansu.awakening.aop;

import com.alibaba.fastjson2.JSONObject;
import com.lansu.awakening.annotation.Log4ai;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Log4ai注解aop
 *
 * @author sulan
 * @date 2023/08/05
 */
@Aspect
@Component
@Slf4j
public class Log4aiAspect {

    /**
     * log4ai切入点
     */
    @Pointcut("@annotation(com.lansu.awakening.annotation.Log4ai)")
    public void log4aiPointcut() {
    }

    /**
     * 环绕日志
     *
     * @param joinPoint 连接点
     * @return Object
     * @throws Throwable 异常
     */
    @Around("log4aiPointcut()")
    public Object aroundLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //1.获取请求的uri 和 请求方式
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String uri = request.getRequestURI();
        String method = request.getMethod();
        //2.获取方法的参数，获取方法描述
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String description = getMethodDescription(joinPoint);
        //3.打印日志
        log.info("请求开始: {}, uri: {}, method: {}, function:{}(){}, args: {}"
                , attributes.getSessionId(), uri, method, signature.getName(), description
                , JSONObject.toJSONString(joinPoint.getArgs()));
        long startTime = System.currentTimeMillis();
        //获取响应结果
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("请求结束: {}, uri: {}, result: {}, 耗时: {}ms"
                , attributes.getSessionId(), uri, JSONObject.toJSONString(result), endTime - startTime);
        return result;
    }

    /**
     * 获取方法描述
     *
     * @param joinPoint 连接点
     * @return {@link String}
     * @throws NoSuchMethodException 没有这样方法例外
     */
    private String getMethodDescription(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        String methodName = joinPoint.getSignature().getName();
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        java.lang.reflect.Method targetMethod = targetClass.getMethod(methodName, parameterTypes);
        if (targetMethod.isAnnotationPresent(Log4ai.class)) {
            Log4ai annotation = targetMethod.getAnnotation(Log4ai.class);
            return annotation.value();
        }
        return "";
    }
}
