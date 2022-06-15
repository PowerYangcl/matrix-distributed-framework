package com.matrix.aspectj;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.util.SignUtil;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @description: 幂等请求拦截器；添加标签 @Idempotent 的接口请求会进入此处
 * 
 * @author Yangcl
 * @date 2022-6-15 16:22:59
 * @home https://github.com/PowerYangcl
 * @path matrix-api / com.matrix.aspectj.IdempotentAspect.java
 * @version 1.6.1.0-Idempotent
 */
@Aspect
@Component
public class IdempotentAspect extends BaseClass{

    private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();

    @Around("@annotation(Idempotent)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    	String redisKey = "";
        try {
        	IdempotentMethod idem = null;
        	MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        	Method method = signature.getMethod();
        	Idempotent ide = method.getAnnotation(Idempotent.class);
        	if(ide.accessToken()) {		// 接口幂等采用token的方式
        		idem = this.buildApi(joinPoint);
        		redisKey = idem.getIden();
        	}else {									// jsp项目采用userInfo.id进行标识
        		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
        		HttpSession session = request.getSession();
        		McUserInfoView userInfo = (McUserInfoView) session.getAttribute("userInfo");
        		idem = this.buildPage(joinPoint, userInfo);
        		if(!idem.isJspSupportIdempotent()) {
        			return joinPoint.proceed();
        		}
        		redisKey = idem.getJspIden();
        	}
        	
        	
        	String value = launch.loadDictCache(DCacheEnum.Idempotent , "").get(redisKey); 
        	if (StringUtils.isNotBlank(value)) {
        		return JSONObject.parseObject(value, Result.class);   
        	}
        	
            Object obj = joinPoint.proceed();
            launch.loadDictCache(DCacheEnum.Idempotent , "").set(redisKey, JSONObject.toJSONString(obj), 60);
            return obj;
        } catch (Exception ex) {
        	ex.printStackTrace();
            throw ex;
        }
    }
    
    private IdempotentMethod buildApi(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        IdempotentMethod method = new IdempotentMethod();
        method.setMethodName(methodName);
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof IdempotentRequest) {
                method.setParam((IdempotentRequest) arg);
            }
        }
        method.setIden(method.getParam().getClientToken());
        return method;
    }

    private IdempotentMethod buildPage(JoinPoint joinPoint, McUserInfoView userInfo) {
        String methodName = joinPoint.getSignature().getName();
        IdempotentMethod method = new IdempotentMethod();
        method.setIden(userInfo.getId() + "");
        method.setMethodName(methodName);
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof IdempotentRequest) {
                method.setParam((IdempotentRequest) arg);
            }
        }
        return method;
    }

    @Data
    private class IdempotentMethod {
        private String iden;	// accessToken or user id
        
        private String methodName;
        
        private IdempotentRequest param;
        
        // jsp层controller请求是否包含入参；如果携带入参同时入参类extends IdempotentRequest.java，且接口方法加了 @Idempotent 标签
        public Boolean isJspSupportIdempotent() {
        	return this.param != null;   // StringUtils.isNotBlank(param.getClientToken());
        }
        
        public String getJspIden() {
        	return this.iden + "-" + this.methodName + "-" + SignUtil.md5Sign(param.toString());
        }
    }
}































