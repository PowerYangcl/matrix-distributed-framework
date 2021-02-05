package com.matrix.cache.redis.core;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import com.matrix.base.BaseClass;

import io.lettuce.core.ReadFrom;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.codec.Utf8StringCodec;
import io.lettuce.core.masterslave.MasterSlave;
import io.lettuce.core.masterslave.StatefulRedisMasterSlaveConnection;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
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
	
    private String host;
    private Integer port;
    private String password;
    private ClientResources resources = null;
	private RedisURI redisUri = null;
	
	// 单机、主从、哨兵三种模式
	private RedisClient client = null;			// 2  创建客户端
	private StatefulRedisConnection<String, String> connect = null;		// 3 创建线程安全的连接
	private RedisCommands<String, String> commands = null;			// 4 创建同步命令
	// Cluster集群模式
	private RedisClusterClient clusterClient = null;
	private StatefulRedisClusterConnection<String, String> clusterConnect = null;
	private RedisAdvancedClusterCommands<String, String> clusterCommands = null;
	
	private String config = "standalone";	// 默认为单机模式

	private RedisTemplateLettuce() {
		this.setHost(this.getConfig("matrix-cache.lettuce_cache_url_" + this.getConfig("matrix-core.model")));
		this.setPort(Integer.valueOf(this.getConfig("matrix-cache.lettuce_port_url_" + this.getConfig("matrix-core.model")))); 
		this.setPassword( this.getConfig("matrix-cache.lettuce_password_url_" + this.getConfig("matrix-core.model")) ); 
		
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
		
		config = this.getConfig("matrix-cache.cache_model_" + this.getConfig("matrix-core.model"));
		switch (config) {
			case "master-slaver":
				this.masterSlaver();
				break;
			case "sentinel":
				this.sentinel();
				break;
			case "cluster":
				this.cluster();
				break;
			default:
				this.standalone();
				break;
		}
	}
	
	/**
	 * @description: 单机模式
	 * 
	 * @author Yangcl
	 * @date 2021-2-2 11:03:19
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 */
	private void standalone () {
		client = RedisClient.create(resources, redisUri);
		connect = client.connect();
		commands = connect.sync(); // 创建同步命令
	}

	/**
	 * @description: 普通主从模式 
	 *  
	 * @author Yangcl
	 * @date 2021-2-1 20:17:42
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 */
	private void masterSlaver() {
		this.standalone();
		
	}
	
	/**
	 * @description: 哨兵模式
	 *  
	 * @author Yangcl
	 * @date 2021-2-1 20:18:10
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 */
	private void sentinel() {
		this.standalone();
		
	}
	
	/**
	 * @description: 集群模式
	 *  
	 * @author Yangcl
	 * @date 2021-2-1 20:18:49
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 */
	private void cluster() {
		clusterClient = RedisClusterClient.create(resources, redisUri);
		clusterConnect = clusterClient.connect();
		clusterCommands = clusterConnect.sync();
	}
	
	private static class LazyHolder{
		private static final RedisTemplateLettuce INSTANCE = new RedisTemplateLettuce();
	}
	
	public static final RedisTemplateLettuce getInstance() {
		return LazyHolder.INSTANCE;
	}

	/**
	 * @description: 资源关闭
	 * 	        关闭连接一般在应用程序停止之前操作，一个应用程序中的一个Redis驱动实例不需要太多的连接（一般情况下只需要一个连接实例就可以，
	 * 	如果有多个连接的需要可以考虑使用连接池，其实Redis目前处理命令的模块是单线程，在客户端多个连接多线程调用理论上没有效果）。
	 * 	关闭客户端一般应用程序停止之前操作，如果条件允许的话，基于后开先闭原则，客户端关闭应该在连接关闭之后操作。
	 * 
	 * @author Yangcl
	 * @date 2021-2-2 11:51:42
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 */
	public Boolean close() {
		
		return true;
	}
	
	public RedisAdvancedClusterCommands<String, String> getClusterCommands() {
		return this.clusterCommands;
	}
	
	public RedisCommands<String, String> getRedisCommands(){
		return commands;
	}
	
}






































