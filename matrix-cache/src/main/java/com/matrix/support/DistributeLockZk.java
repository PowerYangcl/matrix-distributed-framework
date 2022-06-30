package com.matrix.support;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.matrix.base.BaseClass;
import com.matrix.cache.enums.CachePrefix;
import com.matrix.cache.enums.ZkLockPathEnum;

/**
 * @description: 分布式锁支持|zookeeper的高可靠分布式锁。
 *			从性能角度： 缓存 > Zookeeper >= 数据库；从可靠性角度： Zookeeper > 缓存 > 数据库
 *
 *		Zookeeper:
 *				https://blog.csdn.net/chinabestchina/article/details/78659302
 *				http://www.cnblogs.com/LiZhiW/p/4931577.html?utm_source=tuicool&utm_medium=referral
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年9月6日 下午9:22:14 
 * @version 1.0.0.1
 */
public class DistributeLockZk extends BaseClass{

	private CuratorFramework client = null;
	private DistributeLockZk() {
		if(client == null) {		// zk_url_ in config.matrix-cache.properties
			client = CuratorFrameworkFactory.newClient(this.getConfig("matrix-core.zookeeper_host") , new ExponentialBackoffRetry(100, 1) );
			client.start();
		}
	}
	private static class LazyHolder {
		private static final DistributeLockZk INSTANCE = new DistributeLockZk();
	}
	public static final DistributeLockZk getInstance() {
		return LazyHolder.INSTANCE; 
	}
	
	private static final String rootPath = "/" + CachePrefix.zkLockRoot.toString() + "/";     // zookeeper锁基础路径
	
	
	/**
	 * @description: 可重入锁|公平锁(fair)。当多个线程同时竞争一把锁的时候，只有等前一个获取锁的线程释放锁后，下一个线程才能获取锁。
	 * 		使用方法示例：
	 					InterProcessMutex ipm = DistributeLockZk.getInstance().zkMutexLock(ZkLockPathEnum.zkTest);
					 	try{
				            // 获取锁
							ipm.acquire();
				            System.out.println("acquire read lock");
				        } catch (Exception e){
				        	e.printStackTrace();   
				        } finally {
				        	try {
				        		// 释放锁
								ipm.release();
							} catch (Exception e) {
								e.printStackTrace();
							}
				            System.out.println("release read lock");
				        }
	 *	
	 * @test ZkMutexTest.java为此方法的测试类
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
	 * @description: 创建读写锁|公平锁(fair)|同时多个线程可获取读锁，但同时只能有一个线程获取写锁
	  * 		使用方法示例：
	 					InterProcessReadWriteLock ipm = DistributeLockZk.getInstance().readWriteLock(ZkLockPathEnum.zkTest);
					 	try{
				            // 获取锁
							ipm.acquire();
				            System.out.println("acquire read lock");
				        } catch (Exception e){
				        	e.printStackTrace();   
				        } finally {
				        	try {
				        		// 释放锁
								ipm.release();
							} catch (Exception e) {
								e.printStackTrace();
							}
				            System.out.println("release read lock");
				        }
	 *
	 *
	 * @param lock 指定锁路径; 此参数需要定义在ZkLockPathEnum.java中统一管理
	 * @author Yangcl
	 * @date 2018年9月9日 上午12:07:42 
	 * @version 1.0.0.1
	 */
	public InterProcessReadWriteLock readWriteLock(ZkLockPathEnum lock) {
		if(lock == null) {
			return null;
		}
		return new InterProcessReadWriteLock(client , rootPath + lock.toString());
	}
	
	/**
	 * @description: 信号量锁|公平锁(fair)
	 * 	InterProcessSemaphoreV2信号量与多线程中的Semaphonre信息量含义是一致的，即同时最多只能允许指定数量的线程访问临界资源。
	 * 		使用方法示例：
	 					InterProcessSemaphoreV2 ipm = DistributeLockZk.getInstance().semaphoreLock(ZkLockPathEnum.zkTest , 3);
					 	try{
				            // 获取锁
							Lease lease = ipm.acquire();
				            System.out.println("acquire read lock");
				        } catch (Exception e){
				        	e.printStackTrace();   
				        } finally {
				        	try {
				        		// 释放锁
								ipm.returnLease(lease);
							} catch (Exception e) {
								e.printStackTrace();
							}
				            System.out.println("release read lock");
				        }
	 *		        
	 * 			import org.apache.curator.framework.recipes.locks.Lease;
	 * 			Lease - 租约(单个信号)
	 *
	 * @param count 创建信号量，指定同时最大访问个数
	 * @param lock
	 * @author Yangcl
	 * @date 2018年12月10日 下午2:33:54 
	 * @version 1.0.0.1
	 */
	public InterProcessSemaphoreV2  semaphoreLock(ZkLockPathEnum lock , int count) {
		if(lock == null) {
			return null;
		}
		return new InterProcessSemaphoreV2(client , rootPath + lock.toString() , count);
	}
	
}



























































