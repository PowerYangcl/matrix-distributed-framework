package com.matrix.aspectj;

import com.matrix.base.BaseClass;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @description: web service耗时日志
 *
 * @author: liwt
 * @date: 2019/8/20 17:47
 * @version: 1.0.1
 */
@Slf4j
@Aspect
@Component
public class LoggerAspect extends BaseClass{

    /**
     *@description: web service耗时日志
     *
     *@author liwt
     *@date 2019/8/20 17:47
     *@return
     *@version 1.0.1
     */
    public Object aroundMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodAddress = joinPoint.getTarget().getClass().toString()+"."+signature.getMethod().getName();
        try {
            long startTimeMillis = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long execTimeMillis = System.currentTimeMillis() - startTimeMillis;
            log.info("接口地址：" + methodAddress + " 请求耗时：" + execTimeMillis + " ms");
            return result;
        } catch (Throwable te) {
        	log.error(te.getMessage(), te);
            throw new RuntimeException(te.getMessage());
        }
    }
}









