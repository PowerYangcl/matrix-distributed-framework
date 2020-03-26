package com.matrix.support;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.matrix.base.BaseClass;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.cache.redis.core.RedisTemplate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

/**
 * @description: 分布式锁支持|Redis的高性能分布式锁
 * 		【注意！该类废弃，但会一直保留】该类可以作为学习分布式锁来使用，但不建议在项目中继续使用，推荐使用RedissonLock.java
 * 		这里的代码近乎完美的实现了分布式锁的逻辑，但Redisson处理的更加完美。
 * 
 *			从性能角度： 缓存 > Zookeeper >= 数据库；从可靠性角度： Zookeeper > 缓存 > 数据库
 *			可靠性原则：
 *					互斥性。在任意时刻，只有一个客户端能持有锁。
 *					不会发生死锁。即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁。
 *					具有容错性。只要大部分的Redis节点正常运行，客户端就可以加锁和解锁。
 *					加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了。
 *
 *			Redis锁参考如下文章：
 *				https://www.cnblogs.com/number7/p/8320259.html
 *				https://blog.csdn.net/xiaoliu598906167/article/details/82218525| RedisTemplate使用PipeLine的总结，redisTemplate.execute使用
 *				https://www.jianshu.com/p/8cc44d008177
 *
 *			使用示例如下：
				public void redisLock() {
					DistributeLockRedis lock = new DistributeLockRedis("redis-lock-key" , 300 , 200);
					try {
						long now = System.currentTimeMillis();
						if (lock.tryLock()) {
							// TODO 获取到锁要执行的代码块，这里主要写你的业务代码
							System.out.println(now);
						} else {
							// TODO 没有获取到锁要执行的代码块，通常不做任何处理
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
				}
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年12月17日 下午6:41:35
 * @version 1.0.0.1
 */
@Deprecated
public class DistributeLockRedis  extends BaseClass{
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	private StringRedisTemplate redisTemplate = null;
    private static final String LUA_SCRIPT;					// 解锁的lua脚本

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        LUA_SCRIPT = sb.toString();
    }
    
    private String lockKey;	// 锁标志对应的key
    private String lockValue;	// 锁对应的值
    private long expireTime = 60;		// 默认锁的有效时间(m 秒)|redis中的过期时间
    private long timeOut = 200;		// 默认请求锁的超时时间(ms 毫秒)
    private volatile boolean locked = false;		// 锁标记

    /**
     * @description: 锁的过期时间和请求锁的超时时间都是用指定的值
     *
     * @param lockKey			锁的key（Redis的Key）
     * @param expireTime		锁的过期时间(单位：秒)
     * @param timeOut 			请求锁的超时时间(单位：毫秒)
     * @author Yangcl
     * @date 2018年12月17日 下午9:04:15 
     * @version 1.0.0.1
     */
    public DistributeLockRedis(String lockKey, int expireTime, long timeOut) {
    	redisTemplate = RedisTemplate.getInstance().getTemplate();
    	this.lockKey = lockKey;
    	this.expireTime = expireTime;
        this.timeOut = timeOut;
    }
    
	
	 /**
     * @description: 尝试获取锁|超时返回
     *
     * @author Yangcl
     * @date 2018年12月17日 下午3:57:28 
     * @version 1.0.0.1
     */
	public boolean tryLock() {
        lockValue = UUID.randomUUID().toString();					// 生成随机key
        long nowTime = System.currentTimeMillis();					// 系统当前时间 毫秒
        while ((System.currentTimeMillis() - nowTime) < timeOut) {
        	locked = launch.loadDictCache(DCacheEnum.redisLock, "").setnx(lockKey, lockValue, expireTime , "NX", "PX"); 
            if (locked) {
                return locked;	// 上锁成功结束请求
            }
            seleep(50);  // 每次请求等待一段时间
        }
        return locked;
    }
	
	/**
	 * @description: 尝试获取锁|立即返回
	 *
	 * @author Yangcl
	 * @date 2018年12月17日 下午9:59:15 
	 * @version 1.0.0.1
	 */
	public boolean tryLockOne() {
		lockValue = UUID.randomUUID().toString();
		locked = launch.loadDictCache(DCacheEnum.redisLock, "").setnx(lockKey, lockValue, expireTime , "NX", "PX");
		return locked;
	}
	
	/**
	 * @description: 以阻塞方式的获取锁
	 *
	 * @author Yangcl
	 * @date 2018年12月17日 下午10:09:36 
	 * @version 1.0.0.1
	 */
	public boolean tryLockBlock() {
		lockValue = UUID.randomUUID().toString();
        while (true) {
        	locked = launch.loadDictCache(DCacheEnum.redisLock, "").setnx(lockKey, lockValue, expireTime , "NX", "PX"); 	//不存在则添加 且设置过期时间（单位ms）
            if (locked) {
                return locked;
            }
            seleep(50);  // 每次请求等待一段时间
        }
	}
	
	/**
	 * @description: 获取锁状态
	 *
	 * @author Yangcl
	 * @date 2018年12月17日 下午10:25:43 
	 * @version 1.0.0.1
	 */
	public boolean isLock() {
        return locked;
    }
	
	/**
	 * @description: 解锁|Lua 脚本只在客户端传入的值和键的口令串相匹配时，才对键进行删除，
	 * 				可以防止持有过期锁的客户端误删现有锁的情况出现。
	 *
	 * @author Yangcl
	 * @date 2018年12月17日 下午10:33:57 
	 * @version 1.0.0.1
	 */
	public Boolean unlock() {
		if (locked) {	// 只有加锁成功并且锁还有效才去释放锁
			try {
				return this.redisTemplate.execute(new RedisCallback<Boolean>() {
					@Override
					public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
						Object nativeConnection = connection.getNativeConnection();
	                    Long result = 0L;
	                    List<String> keys = new ArrayList<>();
	                    keys.add(launch.loadDictCache(DCacheEnum.redisLock, "").getBaseKey() + lockKey);
	                    List<String> values = new ArrayList<>();
	                    values.add(lockValue);
	                    
	                    if (nativeConnection instanceof JedisCluster) {	// 集群模式
	                        result = (Long) ((JedisCluster) nativeConnection).eval(LUA_SCRIPT, keys, values);
	                    }
	                    if (nativeConnection instanceof Jedis) { 		// 单机模式
	                        result = (Long) ((Jedis) nativeConnection).eval(LUA_SCRIPT, keys, values);
	                    }
	                    if(result == 1) {
	                    	locked = true;
	                    }
	                    return locked;
					}
				});
			} catch (Throwable e) {
				this.getLogger(null).sysoutInfo(this.getInfo(300010110), this.getClass());  // 300010110=Redis不支持EVAL命令，使用降级方式解锁
				String value = launch.loadDictCache(DCacheEnum.redisLock, "").get(lockKey);
				if (lockValue.equals(value)) {
					launch.loadDictCache(DCacheEnum.redisLock, "").del(lockKey);
					return true;
				}
			}
		}
		return locked;
	}
	
	
	/**
	 * @description: 线程等待时间
	 *
	 * @param millis 毫秒
	 * @author Yangcl
	 * @date 2018年12月17日 下午9:14:36 
	 * @version 1.0.0.1
	 */
    private void seleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
            this.getLogger(null).sysoutInfo(this.getInfo(300010111), this.getClass()); // 300010111=Redis分布式锁休眠被中断
        }
    }
	
}












































