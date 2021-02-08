package com.matrix.cache.redis.core.mode;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import com.matrix.cache.inf.IRedisModel;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.resource.DefaultClientResources;

/**
 * @description: Cluster集群模式适配器
 * 		https://www.cnblogs.com/throwable/p/11601538.html
 * 
 * @author Yangcl
 * @date 2021-2-4 10:25:59
 * @home https://github.com/PowerYangcl
 * @path matrix-cache / com.matrix.cache.redis.core.mode.LettuceCluster.java
 * @version 1.0.0.1
 */
public class LettuceCluster extends AbstractLettuceMode{
	
	private RedisClusterClient clusterClient = null;
	private StatefulRedisClusterConnection<String, String> clusterConnect = null;
	private RedisAdvancedClusterCommands<String, String> clusterCommands = null;
	
	public LettuceCluster() {
		super();
		resources = DefaultClientResources.builder()
	    		.ioThreadPoolSize(2)							// I/O线程数 default : Runtime.getRuntime().availableProcessors()
	    		.computationThreadPoolSize(2)		// 任务线程数 default : Runtime.getRuntime().availableProcessors()
	    		.build();
		redisUri = RedisURI.builder()  // 1 创建单机连接的连接信息
	            .withHost(host)
	            .withPort(port)		// .withPassword(password)
	            .withTimeout(Duration.of(2, ChronoUnit.SECONDS))		// 2秒超时
	            .build();
		
		
		
		ClusterTopologyRefreshOptions topologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
		                .enablePeriodicRefresh(Duration.ofMinutes(10))		// 10分钟
		                .enableAllAdaptiveRefreshTriggers()
		                .build();
		clusterClient.setOptions(ClusterClientOptions.builder()
		                       .topologyRefreshOptions(topologyRefreshOptions)
		                       .build());
	}

	public Boolean close() {
		try {
			if(clusterConnect != null && clusterClient != null) {
				clusterConnect.close();
				clusterClient.shutdown();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}




















