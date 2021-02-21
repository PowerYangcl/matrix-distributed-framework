package com.matrix.launch.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.matrix.system.listener.MatrixDistributedFrameworkListener;

/**
 * @description: Spring配置核心入口类
 * 
 * @author Yangcl
 * @date 2021-1-21 18:45:33
 * @home https://github.com/PowerYangcl
 * @path matrix-core/com.matrix.launch.Launch.java
 * @version 1.0.0.1
 */
@Configuration
@ComponentScan({"com.matrix.dao,com.matrix.service,com.matrix.rpcimpl"})
public class ContextLaunch {
	
	@Bean(name = "mdf-listener")
	public MatrixDistributedFrameworkListener initBbean() {
		return new MatrixDistributedFrameworkListener();
	}
	
	// TODO spring boot  配置事务通知  参考
	// 配置文件：https://my.oschina.net/u/2331760/blog/3020092 
	// 注解方式：https://www.cnblogs.com/edi-kai/p/11289701.html  推荐

}
