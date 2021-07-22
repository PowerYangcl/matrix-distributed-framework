package com.matrix.cache.inf;

import org.redisson.config.Config;

import com.matrix.pojo.properties.RedissonProperties;

public interface IRedissonConfigService {
	
	/**
	 * @description: 根据不同的Redis配置策略创建对应的Config
	 * 
	 * @author Yangcl
	 * @date 2021-7-21 16:32:53
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 */
	public Config createRedissonConfig(RedissonProperties redissonProperties);
}
