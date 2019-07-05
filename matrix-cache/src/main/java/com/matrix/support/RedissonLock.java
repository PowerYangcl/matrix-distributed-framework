package com.matrix.support;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import com.matrix.base.BaseClass;

/**
 * @description: 基于Redisson提供更加可靠的分布式锁。相对于DistributeLockRedis.java提供的锁，这种方式更加安全和详细
 * 
 * 	参考链接如下：
 * 		https://www.jianshu.com/p/ae43ed4cf4ae
 * 		https://blog.csdn.net/j3t9z7h/article/details/88000009
 * 		http://www.php.cn/java-article-380297.html
 * 		https://blog.csdn.net/a15835774652/article/details/81775044  【分布式锁（基于redis和zookeeper）详解】
 * 
 * 		具体使用方式如下：
 * 
				public void test() {
					 // RLock类还有很多种锁的实现，请参考API文档。
					RLock disLock = RedissonLock.getInstance().getRedissonClient().getLock("lock-key");
					try {
					    // 尝试获取分布式锁|第一个参数是等待时间，5秒内获取不到锁，则直接返回。 第二个参数 60是60秒后强制释放
						boolean isLock = disLock.tryLock(5L, 60L, TimeUnit.SECONDS);
					    if (isLock) {
					        // TODO if get lock success, do something;
					        
					    }
					} catch (Exception e) {
						e.printStackTrace(); 
					} finally {   // 无论如何, 最后都要解锁
					    disLock.unlock();
					}
				}
 * @author Yangcl
 * @date 2019年6月15日 下午5:49:27 
 * @version 1.0.0.1
 */
public class RedissonLock extends BaseClass{
	
	private Config config = null;
	private RedissonClient redissonClient = null; // // 构造RedissonClient|对外暴露
	
	private RedissonLock() {
		try {
			config = new Config();
			String[] arr = this.getConfig("matrix-cache.cache_url_" + this.getConfig("matrix-core.model")).split(",");
			String [] nodeAddr = new String[arr.length];
			for(int i = 0 ; i < arr.length ; i ++) {
				nodeAddr[i] = "redis://" + arr[i];
			}
			
			config.useClusterServers().addNodeAddress(nodeAddr)
			.setPassword(this.getConfig("matrix-cache.redis_password_" + this.getConfig("matrix-core.model")))
			.setScanInterval(5000);	// Redis集群扫描间隔(毫秒)
			
			redissonClient = Redisson.create(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	private static class LazyHolder {
		private static final RedissonLock INSTANCE = new RedissonLock();
	}
	public static final RedissonLock getInstance() {
		return LazyHolder.INSTANCE; 
	}
	
	public RedissonClient getRedissonClient() {
		return redissonClient;
	}
	
}




























