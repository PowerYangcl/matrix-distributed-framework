package com.matrix.support;

import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.config.Config;

import com.matrix.base.BaseClass;
import com.matrix.cache.redis.core.mode.RedisConnectionProperty;
import com.matrix.cache.redisson.RedisTemplateRedisson;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: 基于Redisson提供更加可靠的分布式锁。相对于DistributeLockRedis.java提供的
 * 		锁，这种方式更加安全和详细。不提倡使用tryLock锁。
 * 
 * @author Yangcl
 * @date 2019年6月15日 下午5:49:27 
 * @version 1.0.0.1
 */
@Slf4j
public class RedissonLock extends BaseClass{
	
	private Redisson redisson;
	
	public RedissonLock(RedisConnectionProperty entity) {
		try {
			Config config = RedisTemplateRedisson.getInstance().createConfig(entity);
			redisson = (Redisson) Redisson.create(config);
        } catch (Exception e) {
            log.error("Redisson init error", e);
            e.printStackTrace();
            throw new IllegalArgumentException("please input correct configurations, connectionType must in standalone/sentinel/cluster/master-replica");
        }
	} 
	
	/**
	 * @description: 提供更加灵活的操作出口
	 * 
	 * @author Yangcl
	 * @date 2021-7-23 16:19:00
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.0.4-redisson
	 */
    public Redisson getRedisson() {
        return redisson;
    }
	
    
	// ===================================================lock锁==================================================    
    /**
     * @description: 加锁操作，设置锁的有效时间，单位秒
     * 
     * @param lockName		 锁名称
     * @param leaseTime 	 锁有效时间
     * @author Yangcl
     * @date 2021-7-23 11:46:52
     * @home https://github.com/PowerYangcl
     * @version 1.6.0.4-redisson
     */
	public void lock(String lockName, long leaseTime) {
		RLock rLock = redisson.getLock(lockName);
		rLock.lock(leaseTime, TimeUnit.SECONDS);		// rLock.lock(); 无参方法默认30秒
	}

	/**
	 * @description: 判断该线程是否持有当前锁
	 * 
	 * @param lockName		锁名称
	 * @author Yangcl
	 * @date 2021-7-23 11:48:56
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.0.4-redisson
	 */
	public boolean isHeldByCurrentThread(String lockName) {
		RLock rLock = redisson.getLock(lockName);
		return rLock.isHeldByCurrentThread();
	}
	
	
	// ===================================================tryLock锁==================================================
	/**
	 * @description: 加锁操作，tryLock锁，没有等待时间
	 * 
	 * @param lockName		锁名称
	 * @param leaseTime	锁有效时间
	 * @author Yangcl
	 * @date 2021-7-23 11:48:36
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.0.4-redisson
	 */
	public boolean tryLock(String lockName, long leaseTime) {
		RLock rLock = redisson.getLock(lockName);
		boolean getLock = false;
		try {
			getLock = rLock.tryLock(leaseTime, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error("获取Redisson分布式锁[异常]，lockName=" + lockName, e);
			e.printStackTrace();
			return false;
		}
		return getLock;
	}

	/**
	 * @description: 加锁操作，tryLock锁，有等待时间
	 * 
	 * @param lockName  锁名称
	 * @param leaseTime 锁有效时间
	 * @param waitTime  等待时间
	 * @author Yangcl
	 * @date 2021-7-23 11:48:41
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.0.4-redisson
	 */
	public boolean tryLock(String lockName, long leaseTime, long waitTime) {
		RLock rLock = redisson.getLock(lockName);
		boolean getLock = false;
		try {
			getLock = rLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error("获取Redisson分布式锁[异常]，lockName=" + lockName, e);
			e.printStackTrace();
			return false;
		}
		return getLock;
	}

	/**
	 * @description: 解锁
	 * 
	 * @param lockName 锁名称
	 * @author Yangcl
	 * @date 2021-7-23 11:48:47
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.0.4-redisson
	 */
	public void unlock(String lockName) {
		redisson.getLock(lockName).unlock();
	}

	/**
	 * @description: 判断该锁是否已经被线程持有
	 * 
	 * @param lockName  锁名称
	 * @return boolean
	 * @author Yangcl
	 * @date 2021-7-23 11:48:52
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.0.4-redisson
	 */
	public boolean isLock(String lockName) {
		RLock rLock = redisson.getLock(lockName);
		return rLock.isLocked();
	}

}




























