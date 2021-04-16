package com.matrix.system;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @description: 封装Spring上下文环境 
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年9月29日 下午2:38:01 
 * @version 1.0.0
 */
public class SpringCtxUtil {

	private static ApplicationContext ctx;
	
	public static void setApplicationContext(ApplicationContext ctx) {
		SpringCtxUtil.ctx = ctx;
	}

	public static ApplicationContext getContext() {
		return ctx;
	}

	public static <T> T getBean(Class<T> clazz) throws BeansException {
		try {
			return (T) ctx.getBean(clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) throws BeansException {
		return (T) ctx.getBean(beanName);
	}
	
}
