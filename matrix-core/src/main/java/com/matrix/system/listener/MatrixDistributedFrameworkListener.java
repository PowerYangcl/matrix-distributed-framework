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
	 */
	public void onApplicationEvent(ContextRefreshedEvent e) {
		if(e.getApplicationContext().getParent() != null) {
			log.info("MatrixDistributedFrameworkListener出现二次调用 ! ! ! ! !");
			return;
		}
		ApplicationContext context = e.getApplicationContext(); 
		SpringCtxUtil.setApplicationContext(context);
		systemInit.initClass();
	}
}
























