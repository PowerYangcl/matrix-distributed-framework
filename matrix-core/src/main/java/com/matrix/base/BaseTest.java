package com.matrix.base;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.matrix.system.SpringCtxUtil;


/**
 * @description: Junit单元测试基类|单元测试需要继承此基类获取上下文环境信息
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年9月29日 下午2:48:56 
 * @version 1.0.0
 */
public class BaseTest implements ApplicationContextAware {

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringCtxUtil.setApplicationContext(context);
	}

}