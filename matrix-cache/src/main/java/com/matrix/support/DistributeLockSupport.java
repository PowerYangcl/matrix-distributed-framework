package com.matrix.support;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.matrix.base.BaseClass;
import com.matrix.cache.enums.ZkLockPathEnum;

/**
 * @description: 基于Zookeeper的高可用分布式锁
 * 	
 * 	有效的解决单点问题，不可重入问题，非阻塞问题以及锁无法释放的问题
 *
 *			    基于zookeeper，curator实现的分布式锁，
 *		包括InterProcessMutex锁、InterProcessReadWriteLock读写锁、
 *		信号量InterProcessSemaphoreV2、 分布式栅栏DistributedBarrier		https://blog.csdn.net/chinabestchina/article/details/78659302
 *		
 *																											dev_zk_host=
 *		
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年9月4日 下午11:56:47 
 * @version 1.0.0.1
 */

/**
 * @description: 分布式锁支持。该类提供2类分布式锁，第一种是基于Redis的高性能分布式锁，
 * 		第二种是基于zookeeper的高可靠分布式锁。请根据实际需要选择。
 *
 *			从性能角度： 缓存 > Zookeeper >= 数据库；从可靠性角度： Zookeeper > 缓存 > 数据库
 *
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年9月6日 下午9:22:14 
 * @version 1.0.0.1
 */
public class DistributeLockSupport extends BaseClass{

	private CuratorFramework client = null;
	private DistributeLockSupport() {
		if(client == null) {
			client = CuratorFrameworkFactory.newClient("10.12.51.139:2181" , new ExponentialBackoffRetry(100, 1) );
			client.start();
		}
	}
	private static class LazyHolder {
		private static final DistributeLockSupport INSTANCE = new DistributeLockSupport();
	}
	public static final DistributeLockSupport getInstance() {
		return LazyHolder.INSTANCE; 
	}
	
	private static final String rootPath = "/" + ZkLockPathEnum.zkLockRoot.toString() + "/";     // zookeeper锁基础路径
	
	
	/**
	 * @description: 可重入锁。当多个线程同时竞争一把锁的时候，只有等前一个获取锁的线程释放锁后，下一个线程才能获取锁。
	 * 		使用方法示例：
	 					InterProcessMutex ipm = DistributeLockSupport.getInstance().zkMutexLock(ZkLockPathEnum.zkTest);
					 	try{
				            // 获取锁
							interProcessMutex.acquire();
				            System.out.println("acquire read lock");
				        } catch (Exception e){
				        	e.printStackTrace();   
				        } finally {
				        	try {
				        		// 释放锁
								interProcessMutex.release();
							} catch (Exception e) {
								e.printStackTrace();
							}
				            System.out.println("release read lock");
				        }
	 *
	 * @param lock 指定锁路径; 此参数需要定义在ZkLockPathEnum.java中统一管理
	 * @author Yangcl
	 * @date 2018年9月6日 下午11:18:46 
	 * @version 1.0.0.1
	 */
	public InterProcessMutex zkMutexLock(ZkLockPathEnum lock) {
		if(lock == null) {
			return null;
		}
		return new InterProcessMutex(client , rootPath + lock.toString());
	}
	
	
	
	/**
	 * @description: 创建读写锁
	 *
	 * @param lock
	 * @author Yangcl
	 * @date 2018年9月9日 上午12:07:42 
	 * @version 1.0.0.1
	 */
	public InterProcessReadWriteLock ReadWriteLock(ZkLockPathEnum lock) {
		if(lock == null) {
			return null;
		}
		return new InterProcessReadWriteLock(client , rootPath + lock.toString());
	}
	
	
	
	
	
	
	
	
	
}



























































