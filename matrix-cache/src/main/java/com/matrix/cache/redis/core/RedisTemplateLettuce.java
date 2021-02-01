package com.matrix.cache.redis.core;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import com.matrix.base.BaseClass;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

/**
 * @description: 支持SpringBoot2.0所使用的新Redis客户端
 * 
 * @author Yangcl
 * @date 2021-2-1 16:03:27
 * @home https://github.com/PowerYangcl
 * @path matrix-cache / com.matrix.cache.redis.core.RedisTemplateLettuce.java
 * @version 1.0.0.1
 */
public class RedisTemplateLettuce extends BaseClass {

	private RedisTemplateLettuce() {
		RedisURI redisUri = RedisURI.builder()                    // <1> 创建单机连接的连接信息
	            .withHost("localhost")
	            .withPort(6379)
	            .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
	            .build();
		RedisClient redisClient = RedisClient.create(redisUri);   // <2> 创建客户端
		StatefulRedisConnection<String, String> connection = redisClient.connect();     // <3> 创建线程安全的连接
	    RedisCommands<String, String> redisCommands = connection.sync();                // <4> 创建同步命令
	    
	    SetArgs setArgs = SetArgs.Builder.nx().ex(5);
	    String result = redisCommands.set("name", "throwable", setArgs);
	    result = redisCommands.get("name");
	    
	    connection.close();   // <5> 关闭连接。关闭连接一般在应用程序停止之前操作，一个应用程序中的一个Redis驱动实例不需要太多的连接（一般情况下只需要一个连接实例就可以，如果有多个连接的需要可以考虑使用连接池，其实Redis目前处理命令的模块是单线程，在客户端多个连接多线程调用理论上没有效果）。
	    redisClient.shutdown();  // <6> 关闭客户端。关闭客户端一般应用程序停止之前操作，如果条件允许的话，基于后开先闭原则，客户端关闭应该在连接关闭之后操作。
	}
	
	private static class LazyHolder{
		private static final RedisTemplateLettuce INSTANCE = new RedisTemplateLettuce();
	}
	
	public static final RedisTemplateLettuce getInstance() {
		return LazyHolder.INSTANCE;
	}

}






































