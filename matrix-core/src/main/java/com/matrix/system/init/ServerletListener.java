package com.matrix.system.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * @descriptions 初始化 加载各种配置和初始化类
 *
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年11月15日 下午9:49:17
 * @version 1.0.1
 */
public class ServerletListener extends ServerletLoader implements ServletContextListener {

	private ServerletLoader contextLoader;

	public void contextInitialized(ServletContextEvent event) {
		if (this.contextLoader == null) {
			this.contextLoader = this;
		}
		this.contextLoader.init(event.getServletContext());
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		this.contextLoader.destory(event.getServletContext());
	}

}





























