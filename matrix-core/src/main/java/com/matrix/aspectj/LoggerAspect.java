package com.matrix.aspectj;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
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
@Aspect
@Component
public class LoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    /**
     *@description:web service耗时日志
     *
     *@param
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
            logger.info(String.format("======>接口地址：%s 请求耗时：%sms\n",methodAddress,execTimeMillis));
            return result;
        } catch (Throwable te) {
            logger.error(te.getMessage(), te);
            throw new RuntimeException(te.getMessage());
        }
    }
}
