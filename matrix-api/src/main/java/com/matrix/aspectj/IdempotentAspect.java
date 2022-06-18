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
 * @description: 幂等请求拦截器；添加标签 @Idempotent 的接口请求会进入此处，同时传入的DTO请求类需要 extends IdempotentRequest.java
 * 		注意：针对Jsp页面传来的接口请求，对应的Controller层的方法中必须传入的两个固定参数：HttpServletRequest request, HttpSession session
 * 					比如：public Result<?> apiWithDtoRequest(JspRequest param, HttpServletRequest request, HttpSession session)   // 这是一个示例
 * 					我们封装参数的请求类最好放在第一个，因为for (Object arg : joinPoint.getArgs()){....} 进行遍历的时候第一个就是我们需要的值，可以提升效率
 * 		
 * 		使用场景：参考IdempotentTestController.java
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
    
    /**
     * @description: 构造来自open-api的接口请求
     * 
     * @author Yangcl
     * @date 2022-6-18 21:08:20
     * @home https://github.com/PowerYangcl
     * @version 1.6.1.0-Idempotent
     */
    private IdempotentMethod buildApi(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        IdempotentMethod method = new IdempotentMethod();
        method.setMethodName(methodName);
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof IdempotentRequest) {
                method.setParam((IdempotentRequest) arg);
                break;
            }
        }
        method.setIden(method.getParam().getClientToken());
        return method;
    }

    /**
     * @description: 构造来自jsp页面的接口请求
     * 
     * @author Yangcl
     * @date 2022-6-18 21:08:20
     * @home https://github.com/PowerYangcl
     * @version 1.6.1.0-Idempotent
     */
    private IdempotentMethod buildPage(JoinPoint joinPoint, McUserInfoView userInfo) {
        String methodName = joinPoint.getSignature().getName();
        IdempotentMethod method = new IdempotentMethod();
        method.setIden(userInfo.getId() + "");
        method.setMethodName(methodName);
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof IdempotentRequest) {
                method.setParam((IdempotentRequest) arg);
                break;
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
        
        // this.methodName.toLowerCase()，如果包含驼峰则设置缓存时失败，很奇怪。。
        public String getJspIden() {
        	return this.iden + "-" + this.methodName.toLowerCase() + "-" + SignUtil.md5Sign(param.toString());
        }
    }
}































