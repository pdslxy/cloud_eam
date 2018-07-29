package com.enerbos.cloud.eam.openservice.config;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.common.EnerbosMessage;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<Long>();


    @Pointcut("execution(public * com.enerbos.cloud.*.client..*Fallback.*(..))")
    public void feignLog() {
    }

    @Pointcut("execution(public * com.enerbos.cloud.eam.openservice.controller..*Controller.*(..))")
    public void controllerLog() {
    }


    @Around("feignLog()")
    public Object doAroundFeignClient(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] arguments = joinPoint.getArgs();
        Throwable cause = new Throwable();
        for (Object object : arguments) {
            if (object.toString().contains("feign.RetryableException")) {
                throw new EnerbosException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "服务连接失败");
            }
            cause = (Throwable) object;
        }
        if (cause != null && cause.getMessage() != null) {
            throw cause;
        }
        return joinPoint.proceed();
    }


    @Around("controllerLog()")
    public Object afterController(ProceedingJoinPoint joinPoint) throws Throwable {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            logger.error("[ class：" + targetName + " ]");
            logger.error("[ method：" + methodName + " ]");
            logger.error("[ args : " + Arrays.toString(joinPoint.getArgs()) + " ]");
            try {
                if (e instanceof HystrixRuntimeException) {
                    if (e.getCause().getMessage().contains("MyBatisSystemException")) {
                        logger.error("[ 异常信息：数据库查询异常;" + e.getMessage() + "]");
                        return EnerbosMessage.createErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "数据库查询异常", e.getCause().getMessage());
                    }
                    if (e.getCause().getMessage().contains("NullPointerException")) {
                        logger.error("[ 异常信息：数据空指针异常;" + e.getMessage() + "]");
                        return EnerbosMessage.createErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "数据空指针异常", e.getCause().getMessage());
                    }
                    if (e.getCause().getMessage().contains("EnerbosException")) {
                        String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
                        JSONObject jsonMessage = JSONObject.parseObject(message);
                        logger.error("[ 异常信息：已知抛出的异常;" + jsonMessage.get("message").toString() + "]");

                        return EnerbosMessage.createErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR.toString(), jsonMessage.get("message").toString(), e.getCause().getMessage());
                    }
                    logger.error("[ 异常信息：通讯异常;" + e.getMessage() + "]");
                    return EnerbosMessage.createErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "通讯异常", e.getCause().getMessage());
                } else if (e instanceof NullPointerException) {
                    logger.error("[ 异常信息：数据异常;" + e.getMessage() + "]");
                    return EnerbosMessage.createErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "数据异常", e.getLocalizedMessage());
                } else if (e instanceof EnerbosException) {
                    logger.error("[ 异常信息：已知异常;" + e + "]");
                    return EnerbosMessage.createErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getLocalizedMessage(), e.getLocalizedMessage());
                } else if (e instanceof ClassCastException) {
                    logger.error("[ 异常信息：类型转换异常;" + e + "]");
                    return EnerbosMessage.createErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "类型转换异常", e.getLocalizedMessage());
                } else {
                    logger.error("[ 异常信息：未知异常;" + e.getMessage() + "]");
                    return EnerbosMessage.createErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "未知异常", e.getCause().getMessage());

                }
            } catch (Exception elog) {
                logger.error("[ logAspect out exception]", elog);
            }
        }
        return result;
    }

}