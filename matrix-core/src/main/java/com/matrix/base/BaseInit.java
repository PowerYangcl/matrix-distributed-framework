package com.matrix.base;

import com.matrix.base.interfaces.IBaseCache;

/**
 * @description: 系统初始化调用基类|继承此类将随项目启动而顺序加载
 *
 * @author Yangcl
 * @date 2018年11月16日 下午3:35:28 
 * @version 1.0.0.1
 */
public abstract class BaseInit extends BaseClass{
	
	/**
	 * @description: 初始化配置文件中的信息到缓存中
	 * 
	 * @param subCaches
	 * @author Yangcl 
	 * @date 2016年11月29日 下午2:56:27 
	 * @version 1.0.0.1
	 */
	public void initEcache(IBaseCache<?>... subCaches) {
		for (IBaseCache<?> cache : subCaches) {
			cache.refresh(null);
		}
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
