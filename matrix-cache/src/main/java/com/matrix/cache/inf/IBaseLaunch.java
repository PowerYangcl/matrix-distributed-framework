package com.matrix.cache.inf;

import com.matrix.cache.enums.CachePrefix;

public interface IBaseLaunch<T> {
	
	/**
	 * @descriptions 操作字典相关的缓存数据，仅限于matrix-distributed-framework项目内部使用。
	 *
	 * @param prefix
	 * @param load 获取缓存失败时组织缓存的类名称
	 * 
	 * @param load 缓存load类|除去操作缓存的get行为，该参数均为null。
	 * 
	 * @date 2016年12月12日 下午10:55:58
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public T loadDictCache(CachePrefix prefix , String load); 
	
	/**
	 * @description: 操作业务系统的服务缓存
	 *
	 * @author Yangcl
	 * @date 2022-6-26 20:44:40
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.1.0-redis-enum
	 */
	public T loadServiceCache(String prefix , String load);
	
	/**
	 * @description: 操作业务系统的字典缓存
	 *
	 * @author Yangcl
	 * @date 2022-6-26 20:45:13
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.1.0-redis-enum
	 */
	public T loadDictCache(String prefix , String load); 

	
	
	
	
	
	
	
}


