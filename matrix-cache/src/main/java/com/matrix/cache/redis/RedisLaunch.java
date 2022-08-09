package com.matrix.cache.redis;

import org.apache.commons.lang3.StringUtils;

import com.matrix.cache.enums.CachePrefix;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.cache.redis.core.RedisFactory;

/**
 * @descriptions redis核心工具类，供外部调用。
 * 
 * @test String value = CacheLaunch.getInstance().Launch().loadDictCache("cachePrefix-01" , "").get(xs-Test-001);
 *
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年12月11日 下午4:56:24
 * @version 1.0.1
 */
public class RedisLaunch implements IBaseLaunch<ICacheFactory> { 

	/**
	 * @descriptions 操作字典相关的缓存数据，仅限于matrix-distributed-framework项目内部使用。
	 *
	 * @param enum_
	 * @param load 缓存load类|除去操作缓存的get行为，该参数均为null。
	 * 
	 * @date 2016年12月12日 下午10:55:58
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public RedisFactory loadDictCache(CachePrefix prefix , String load) {
		return new RedisFactory("xd-" + prefix + "-" , load);
	}

	
	
	
	/**
	 * @description: 操作业务系统的服务缓存
	 *
	 * @author Yangcl
	 * @date 2022-6-26 20:44:40
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.1.0-redis-enum
	 */
	@Override
	public RedisFactory loadServiceCache(String prefix, String load) {
		if(StringUtils.isBlank(prefix) || !prefix.startsWith("S")) {
			return null;			// TODO 最好能够跑出异常
		}
		return new RedisFactory("xs-" + prefix + "-" , load);
	}

	/**
	 * @description: 操作业务系统的字典缓存
	 *
	 * @author Yangcl
	 * @date 2022-6-26 20:45:13
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.1.0-redis-enum
	 */
	@Override
	public RedisFactory loadDictCache(String prefix, String load) {
		if(StringUtils.isBlank(prefix) || !prefix.startsWith("D")) {
			return null;
		}
		return new RedisFactory("xd-" + prefix  + "-" , load);
	}
	
	
	
	
	
}























