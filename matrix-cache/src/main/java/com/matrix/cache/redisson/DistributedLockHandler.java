package com.matrix.cache.redisson;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.matrix.annotation.DistributedLock;
import com.matrix.support.RedissonLock;

/**
 * @description: Redisson分布式锁注解解析器
 * 
 * @author Yangcl
 * @date 2021-7-23 17:45:50
 * @home https://github.com/PowerYangcl
 * @path matrix-cache / com.matrix.cache.redisson.RedissonManager.java
 * @version 1.6.0.4-redisson
 */
@Aspect
public class DistributedLockHandler {

	@Autowired
	private RedissonLock redissonLock;
	
    @Around("@annotation(distributedLock)")
    public void distributedAround(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) {
    	String lockName = distributedLock.value();		// 获取锁名称
        try {
        	int leaseTime = distributedLock.leaseTime();		// 获取超时时间，默认10秒
        	redissonLock.lock(lockName, leaseTime);
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            if (redissonLock.isHeldByCurrentThread(lockName)) {
                redissonLock.unlock(lockName);
            }
        }
    }
}
















