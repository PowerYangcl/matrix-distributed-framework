package com.matrix.cache.redis.core.mode;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import com.matrix.cache.inf.IRedisModel;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.resource.DefaultClientResources;

/**
 * @description: 哨兵模式适配器
 * 
 * @author Yangcl
 * @date 2021-2-4 10:25:59
 * @home https://github.com/PowerYangcl
 * @path matrix-cache / com.matrix.cache.redis.core.mode.LettuceCluster.java
 * @version 1.0.0.1
 */
public class LettuceSentinel extends AbstractLettuceMode {

	// 单机、主从、哨兵三种模式
	private RedisClient client = null;			// 2  创建客户端
	private StatefulRedisConnection<String, String> connect = null;		// 3 创建线程安全的连接
	private RedisCommands<String, String> commands = null;			// 4 创建同步命令
	
	public LettuceSentinel() {
		super();
		resources = DefaultClientResources.builder()
	    		.ioThreadPoolSize(2)							// I/O线程数 default : Runtime.getRuntime().availableProcessors()
	    		.computationThreadPoolSize(2)		// 任务线程数 default : Runtime.getRuntime().availableProcessors()
	    		.build();
		redisUri = RedisURI.builder()  // 1 创建单机连接的连接信息
	            .withHost(host)
	            .withPort(port)		// .withPassword(password)
	            .withTimeout(Duration.of(2, ChronoUnit.SECONDS))		// 2秒超时
//	            .withSentinelMasterId("sentinelMasterId")
	            .build();
		
		client = RedisClient.create(resources, redisUri);
		connect = client.connect();
		commands = connect.sync(); // 创建同步命令
	}

	public Boolean close() {
		try {
			if(connect != null && client != null) {
				connect.close();   // 5 关闭连接。
				client.shutdown();  // 6 关闭客户端。
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}




















