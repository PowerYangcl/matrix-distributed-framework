package com.matrix.base.interfaces;

/**
 * @description: 缓存操作接口
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年9月29日 下午4:36:43 
 * @version 1.0.0
 */
public interface IBaseCache<K> {

	/**
	 * @description: 刷新缓存 该方法一般定义为synchronized 
	 * 
	 * @author Yangcl 
	 * @date 2016年9月29日 下午4:37:00 
	 * @version 1.0.0.1
	 */
	public void refresh(K key);
	
	
	/**
	 * @description: 删除所有缓存
	 * 
	 * @author Yangcl 
	 * @date 2016年9月29日 下午4:37:18 
	 * @version 1.0.0.1
	 */
	public void removeAll();
	
	
}
















