package com.matrix.system.listener;

import com.matrix.base.BaseLog;
import com.matrix.system.SpringCtxUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @description: 基于dubbo分布式的扩展支持，随项目启动而加载；为每一个dubbo服务个性化定制。
 *
 * @author Yangcl
 * @date 2018年9月14日 下午7:28:33 
 * @version 1.0.0.1
 */
public class MatrixDistributedFrameworkListener implements ApplicationListener<ContextRefreshedEvent>{

	public void onApplicationEvent(ContextRefreshedEvent e) {
		if(e.getApplicationContext().getParent() != null) {
			BaseLog.getInstance().setLogger(null).sysoutInfo("MatrixDistributedFrameworkListener出现二次调用 ! ! ! ! !" , this.getClass()); 
			return;
		}
		ApplicationContext context = e.getApplicationContext(); 
		SpringCtxUtil.setApplicationContext(context);
	}
}
























