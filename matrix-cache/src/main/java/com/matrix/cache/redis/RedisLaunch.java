package com.matrix.cache.redis;

import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.enums.SCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.cache.redis.core.RedisFactory;

/**
 * @descriptions redis核心工具类，供外部调用。
 * 
 * @test String value = RedisLaunch.initServiceCache(SCacheEnum.Test).getCache("xs-Test-001");
 *
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年12月11日 下午4:56:24
 * @version 1.0.1
 */
public class RedisLaunch implements IBaseLaunch<ICacheFactory> { 
	

	/**
	 * @descriptions 操作业务相关的缓存数据
	 *
	 * @param enum_
	 * @param load 缓存load类|除去操作缓存的get行为，该参数均为null。
	 * 
	 * @date 2016年12月12日 下午10:55:31
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public RedisFactory loadServiceCache(SCacheEnum enum_ , String load) {
		return new RedisFactory("xs-" + enum_.toString() + "-" , load);
	}

	/**
	 * @descriptions 操作字典相关的缓存数据
	 *
	 * @param enum_
	 * @param load 缓存load类|除去操作缓存的get行为，该参数均为null。
	 * 
	 * @date 2016年12月12日 下午10:55:58
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public RedisFactory loadDictCache(DCacheEnum enum_ , String load) {
		return new RedisFactory("xd-" + enum_.toString() + "-" , load);
	}

}





