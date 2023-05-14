package com.matrix.cache.redisson;

import org.redisson.config.Config;

import com.matrix.base.BaseClass;
import com.matrix.cache.inf.IRedissonConfigService;
import com.matrix.cache.redis.core.mode.RedisConnectionProperty;
import com.matrix.cache.redisson.mode.ClusterConfigImpl;
import com.matrix.cache.redisson.mode.MasterslaveConfigImpl;
import com.matrix.cache.redisson.mode.SentineConfigImpl;
import com.matrix.cache.redisson.mode.StandaloneConfigImpl;
import com.matrix.pojo.properties.RedissonProperties;

/**
 * @description: Redisson核心配置，用于提供初始化的redisson实例
 * 		参考：https://www.cnblogs.com/qdhxhz/p/11059200.html
 * 					https://github.com/yudiandemingzi
 * 
 * @author Yangcl
 * @date 2021-7-21 16:06:28
 * @home https://github.com/PowerYangcl
 * @path matrix-cache / com.matrix.cache.redisson.RedissonManager.java
 * @version 1.6.0.4-redisson
 */
public class RedisTemplateRedisson extends BaseClass {
	
	// // 连接类型，支持standalone-单机节点，sentinel-哨兵，cluster-集群，master-replica-主从
	public Config createConfig(RedisConnectionProperty config) {
		RedissonProperties properties = new RedissonProperties();
		properties.setType(config.getModel());		// sentinel or cluster and so on.
		String url = config.getHost();
		String port = config.getPort();
		properties.setAddress(url + ":" + port);
		properties.setUsername(config.getName());
		properties.setPassword(config.getPassword());
		properties.setDatabase(Integer.valueOf(config.getDefalutDb()) + 1);
		
		IRedissonConfigService redissonConfigService = null; 
		switch (properties.getType()) {
			case "master-replica":
				redissonConfigService = new MasterslaveConfigImpl();
				break;
			case "sentinel":
				redissonConfigService = new SentineConfigImpl();
				break;
			case "cluster":
				redissonConfigService = new ClusterConfigImpl();
				break;
			default:
				redissonConfigService = new StandaloneConfigImpl();
				break;
		}
		return redissonConfigService.createRedissonConfig(properties);
	}
	
	private RedisTemplateRedisson() {
    }
	private static class LazyHolder {
		private static final RedisTemplateRedisson INSTANCE = new RedisTemplateRedisson();
	}
	public static final RedisTemplateRedisson getInstance() {
		return LazyHolder.INSTANCE; 
	}
}




























