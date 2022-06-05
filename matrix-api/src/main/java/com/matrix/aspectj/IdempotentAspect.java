package com.matrix.aspectj;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.matrix.base.BaseClass;
import com.matrix.pojo.view.McUserInfoView;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Aspect
@Component
public class IdempotentAspect extends BaseClass{

    @Autowired
    private StringRedisTemplate redis_template;

    @Around("@annotation(Idempotent)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    	String idempotent_key = "";
    	MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    	Method method = signature.getMethod();
    	Idempotent ide = method.getAnnotation(Idempotent.class);
    	if(ide.accessToken()) {		// 接口幂等采用token的方式
    		// TODO 再次设计下，有不合理的地方
    		
    		
    		
    	}else {		// jsp项目采用userInfo.id进行标识
    		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
    		HttpSession session = request.getSession();
    		McUserInfoView userInfo = (McUserInfoView) session.getAttribute("userInfo");
    		IdempotentMethod idem = this.build(joinPoint, userInfo);
    		if (StringUtils.isBlank(idem.idempotentKey())) {
    			return joinPoint.proceed();
    		}
    		idempotent_key = idem.idempotentKey();
    	}
    	
    	String redisKey = idempotent_key;			// TODO 判断是否为空
    	
        String value = redis_template.opsForValue().get(idempotent_key);
        if (StringUtils.isNotBlank(value)) {
        	
        }
        try {
            Object obj = joinPoint.proceed();
            return obj;
        } catch (Exception ex) {
        	
            throw ex;
        }
    }

    private IdempotentMethod build(JoinPoint joinPoint, McUserInfoView userInfo) {
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

        public boolean supportIdempotent() {
            return param != null && StringUtils.isNotBlank(param.getClientToken());
        }

        public String idempotentKey() {
            if (!supportIdempotent()) {
                return null;
            }
            return String.format("idemp-%s-%s-%s", iden, methodName, param.getClientToken());
        }
    }
}































