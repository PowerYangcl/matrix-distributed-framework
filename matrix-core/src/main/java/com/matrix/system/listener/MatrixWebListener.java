package com.matrix.system.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.matrix.base.BaseClass;


/**
 * @descriptions 初始化监听ServletContext上下文环境
 * 		当运行一个Tomcat容器中的web项目时，这个监听类需要配置到web.xml文件中，如：
 			<listener>
				<listener-class>com.matrix.system.listener.ServerletListener</listener-class>
			</listener>
 *			由于Servlet生命周期的顺序性，故这个监听需要配置在ContextLoaderListener监听器的后面
 *
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2018年12月21日 上午11:34:26
 * @version 1.0.0.1
 */
public class MatrixWebListener extends BaseClass implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {
		ServletContext application = event.getServletContext(); 
		application.setAttribute("ssl", this.getConfig("matrix-core.ssl_" + this.getConfig("matrix-core.model")));
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}
}





























