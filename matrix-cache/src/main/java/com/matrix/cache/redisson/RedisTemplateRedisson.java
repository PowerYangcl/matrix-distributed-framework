package com.matrix.cache.redisson;

import org.redisson.config.Config;

import com.matrix.base.BaseClass;
import com.matrix.cache.inf.IRedissonConfigService;
import com.matrix.cache.redisson.mode.ClusterConfigImpl;
import com.matrix.cache.redisson.mode.MasterslaveConfigImpl;
import com.matrix.cache.redisson.mode.SentineConfigImpl;
import com.matrix.cache.redisson.mode.StandaloneConfigImpl;
import com.matrix.pojo.properties.RedissonProperties;

public class RedisTemplateRedisson extends BaseClass {
	
	private RedisTemplateRedisson() {
    }
	private static class LazyHolder {
		private static final RedisTemplateRedisson INSTANCE = new RedisTemplateRedisson();
	}
	public static final RedisTemplateRedisson getInstance() {
		return LazyHolder.INSTANCE; 
	}
	
	
	public Config createConfig() {
		String type = this.getConfig("matrix-cache.redis_model_" + this.getConfig("matrix-core.model"));		// sentinel or cluster and so on.
		RedissonProperties properties = new RedissonProperties();
		properties.setType(type);	// 连接类型，支持standalone-单机节点，sentinel-哨兵，cluster-集群，master-replica-主从
		properties.setAddress(this.getConfig("matrix-cache.redis_url_" + this.getConfig("matrix-core.model")));
		properties.setUsername(this.getConfig("matrix-cache.redis_username_" + this.getConfig("matrix-core.model")));
		properties.setPassword(this.getConfig("matrix-cache.redis_password_" + this.getConfig("matrix-core.model")));
		properties.setDatabase(1);
		
		IRedissonConfigService redissonConfigService = null; 
		switch (type) {
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
	
	
	
	
}




























