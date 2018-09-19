package com.matrix.system.init;

import org.apache.log4j.Logger;

import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseCache;

/**
 * @descriptions 初始化调用类的顶层父类
 *
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年11月13日 下午12:10:01
 * @version 1.0.1
 */
public abstract class RootInit extends BaseClass {
	
	private static Logger logger = Logger.getLogger(RootInit.class);
	
	public RootInit() {
		this.getLogger(logger).logInfo(0, "初始化调用类 : " + this.getClass().getName());
	}
 
 
	/**
	 * @description: 初始化配置文件中的信息到缓存中
	 * 
	 * @param subCaches
	 * @author Yangcl 
	 * @date 2016年11月29日 下午2:56:27 
	 * @version 1.0.0.1
	 */
	public void rootInitCache(IBaseCache... subCaches) {
		for (IBaseCache cache : subCaches) {
			cache.refresh();
		}
	}

	public boolean init() {
		return onInit();
	}

	public boolean destory() {
		return onDestory();
	}
 
	/**
	 * @descriptions 当系统初始化时调用
	 *
	 * @return
	 * @date 2016年11月13日 下午1:39:30
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public abstract boolean onInit();
	
	/**
	 * @descriptions 当容器关闭时调用
	 *
	 * @return
	 * @date 2016年11月13日 下午1:39:46
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public abstract boolean onDestory();

}














