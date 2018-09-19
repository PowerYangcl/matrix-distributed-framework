package com.matrix.base.interfaces;

/**
 * @description: 针对缓存取值时候出现空值的问题。实现该接口的类会去加载这条缓存。
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年11月20日 下午4:54:45 
 * @version 1.0.0.1
 */
public interface ILoadCache {
	
	/**
	 * @description: reload the cache in you system.
	 *
	 * @param key 缓存主键
	 * @param field if type=hash 则field生效
	 * @return 
	 * @author Yangcl
	 * @date 2017年11月20日 下午5:33:37 
	 * @version 1.0.0.1
	 */
	public String load(String key , String field); 
}
