package com.matrix.rocket;

import org.apache.rocketmq.client.producer.TransactionListener;

import com.matrix.base.BaseClass;
import com.matrix.base.BaseTransactionMqProducer;
import com.matrix.gtt.GttEnum;
import com.matrix.listener.TransactionListenerImpl;

/**
 * @description: 【事务消息】生产者使用示例。为了保证每一个生产者对象不被频繁的创建和修改，
 * 		故使用单例模式来约束每一个BaseTransactionMqProducer.java或BaseMqProducer.java
 * 		即：每创建一种业务需要的Producer都是以一个单例类来进行包装和约束。
 * 		通常来讲，这个单例类应该写在每一个具体的业务项目中。
 * 
 * 		命名原则：GttEnum.java中的GroupTransTest+Rocket共同组成这个单例类：GroupTransTestRocket.java
 * 		
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2019年6月27日 上午12:08:09 
 * @version 1.0.0.1
 */
public class GroupTransTestRocket extends BaseClass {
	
	private BaseTransactionMqProducer transProducer = null;
	
	private GroupTransTestRocket() {
		TransactionListener transactionListener = new TransactionListenerImpl();
		transProducer = new BaseTransactionMqProducer(transactionListener , GttEnum.GroupTransTest);
	} 
	private static class LazyHolder {
		private static final GroupTransTestRocket INSTANCE = new GroupTransTestRocket();
	}
	public static final GroupTransTestRocket getInstance() {
		return LazyHolder.INSTANCE; 
	}
	
	/**
	 * @description: 对外暴露生产者句柄
	 *
	 * @author Yangcl
	 * @date 2019年6月26日 下午5:49:42 
	 * @version 1.0.0.1
	 */
	public BaseTransactionMqProducer getTransProducer() {
		return transProducer;
	}
	
	
	
	
}
