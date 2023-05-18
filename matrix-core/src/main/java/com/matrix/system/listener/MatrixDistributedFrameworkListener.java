package com.matrix.system.listener;

import com.matrix.system.SpringCtxUtil;
import com.matrix.system.init.SystemInit;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @description: 获取上下文环境
 *
 * @author Yangcl
 * @date 2018年9月14日 下午7:28:33 
 * @version 1.0.0.1
 */
@Slf4j
public class MatrixDistributedFrameworkListener implements ApplicationListener<ContextRefreshedEvent>{

	private SystemInit systemInit;
	public MatrixDistributedFrameworkListener(SystemInit systemInit) {
		this.systemInit = systemInit;
	}

	/**
	 * @description: 初始化配置文件中的类
	 *
	 * @author Yangcl
	 * @date 22018年9月14日 下午12:41:19
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 * 
	 * @description: 由于引入spring-cloud-starter-bootstrap模块去加载nacos中的配置文件，此处会导致【首次】加载上下文环境
	 * 		时e.getApplicationContext().getParent() != null，所以会导致bean加载失败，故增加额外判断：&& SpringCtxUtil.getContext() != null
	 * 
	 * @author Yangcl
	 * @date 2023-5-18 23:14:41
	 * @version v1.6.2-scII-version
	 */
	public void onApplicationEvent(ContextRefreshedEvent e) {
		if(e.getApplicationContext().getParent() != null && SpringCtxUtil.getContext() != null) {
			log.info("MatrixDistributedFrameworkListener出现二次调用 ! ! ! ! !");
			return;
		}
		log.info("MatrixDistributedFrameworkListener 第一次调用，ApplicationContext 上下文环境加载完成。");
		ApplicationContext context = e.getApplicationContext(); 
		SpringCtxUtil.setApplicationContext(context);
		systemInit.initClass();
	}
}
























