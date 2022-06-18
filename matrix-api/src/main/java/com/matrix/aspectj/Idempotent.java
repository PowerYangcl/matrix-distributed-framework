package com.matrix.aspectj;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 添加、修改、删除等需要进行幂等限制的接口行为，
 * 		其controller层方法需要添加此注释，由IdempotentAspect.java统一进行拦截。
 * 		放置于 @RequestMapping 等相关注解之上即可。
 * 
 * @author Yangcl
 * @date 2022-5-29 18:16:28
 * @home https://github.com/PowerYangcl
 * @path matrix-web-adapter / com.matrix.aspectj.Idempotent.java
 * @version 1.6.1.0-Idempotent
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
	
	public boolean accessToken() default false;   // true：open-api请求；false：JSP页面的常规Controller请求
	
}
