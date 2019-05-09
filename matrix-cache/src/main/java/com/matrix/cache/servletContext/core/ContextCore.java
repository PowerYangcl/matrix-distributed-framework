package com.matrix.cache.servletContext.core;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.matrix.base.BaseClass;

/**
 * @descriptions 提供ServletContext变量
 * 	调用方式如下：ServletContext context = ApplicationCore.getInstance().getApplication();
 * 
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2017年4月11日 下午10:43:55
 * @version 1.0.1
 */

public class ContextCore extends BaseClass{
	private WebApplicationContext webApplicationContext;
	private ServletContext context;
	private ContextCore() { 
		if (webApplicationContext == null) { // 创建全局缓存 - Yangcl
			webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			context = webApplicationContext.getServletContext();
		}
	}
	private static class LazyHolder {
		private static final ContextCore INSTANCE = new ContextCore();
	}
	public static final ContextCore getInstance() {
		return LazyHolder.INSTANCE; 
	}
	
	public ServletContext getApplication() {
		return context;
	}
	
}
