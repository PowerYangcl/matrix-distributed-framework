package com.matrix.cache.redis.core;

import com.matrix.base.BaseClass;
import com.matrix.cache.inf.IRedisModel;
import com.matrix.cache.redis.core.mode.LettuceCluster;
import com.matrix.cache.redis.core.mode.LettuceMasterReplica;
import com.matrix.cache.redis.core.mode.LettuceSentinel;
import com.matrix.cache.redis.core.mode.LettuceStandalone;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 支持SpringBoot2.0所使用的新Redis客户端 | 仅支持同步命令，异步命令请参考实现类：RedisTemplateLettuceAsync.java
 * 		支持：单机、主从、哨兵和集群4中Redis模式。
 * 
 * 		参考：https://www.cnblogs.com/throwable/p/11601538.html
 * 					https://github.com/it235/knife4j-redis-lettuce
 * 					https://my.oschina.net/u/3343218/blog/2989564
 * 					https://www.lskyf.com/post/68
 * 					https://my.oschina.net/u/3266761/blog/3023454
 * 		A. 如果只需备份，偶尔才用到的数据，异步存起来就可以了。
 * 		B. 如果一个线程里需要使用get、hget、hgetall等方法，但是同时也有别的事情要做，那么可以使用异步获取；得到future对象后，把其他事情做完再进行阻塞等待。
 * 
 * @author Yangcl
 * @date 2021-2-1 16:03:27
 * @home https://github.com/PowerYangcl
 * @path matrix-cache / com.matrix.cache.redis.core.RedisTemplateLettuce.java
 * @version 1.0.0.1
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RedisTemplateLettuce extends BaseClass {
	
	private IRedisModel redisModel;
	private String config = "standalone";	// 默认为单机模式

	private RedisTemplateLettuce() {
		config = this.getConfig("matrix-cache.cache_model_" + this.getConfig("matrix-core.model"));
		switch (config) {
			case "master-replica":
				redisModel = new LettuceMasterReplica();
				break;
			case "sentinel":
				redisModel = new LettuceSentinel();
				break;
			case "cluster":
				redisModel = new LettuceCluster();
				break;
			default:
				redisModel = new LettuceStandalone();
				break;
		}
	}
	
	private static class LazyHolder{
		private static final RedisTemplateLettuce INSTANCE = new RedisTemplateLettuce();
	}
	
	public static final IRedisModel getInstance() {
		return LazyHolder.INSTANCE.getRedisModel();
	}

	
	
	
}






































