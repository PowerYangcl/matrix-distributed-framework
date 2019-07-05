package com.matrix.base;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import com.alibaba.fastjson.JSONObject;

/**
 * @description: RockMq【事物消息】顶层提供者，封装基础行为。
 * 	代码存放位置：约定在每个具体Dubbo服务的com.matrix.mq包下
 * 	使用原则：消息队列的使用不与业务代码强耦合
 * 
 * 	RocketMQ最终一致性的实现方式：
 * 			 producer第一阶段发送Prepared消息时，会拿到消息的地址，第二阶段执行本地事物，第三阶段通过第一阶段拿到的地址去访问消息，并修改消息的状态；
 * 			 如果producer端的确认消息发送失败了，RocketMQ会定期扫描消息集群中的事物消息，如果发现了Prepared消息，它会向消息发送端(生产者)确认，A的
 * 			 钱到底是减了还是没减呢？如果减了是回滚还是继续发送确认消息呢？RocketMQ会根据发送端(生产者)设置的策略来决定是回滚还是继续发送确认消息。
 * 			 这样就保证了消息发送与本地事务同时成功或同时失败。
 * 
 * 	使用示例请参考：com.matrix.rocket.GroupTransTestSupport.java
 * 									com.matrix.service.impl.ExampleServiceImpl.ajaxRocketmqProducerInit()
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2019年4月24日 下午4:59:02 
 * @version 1.0.0.1
 */
public class BaseTransactionMqProducer extends BaseClass{

	private TransactionMQProducer producer = null;
	private TransactionSendResult sendResult = null;
	
	
	/**
	 * @description: 初始化Producer, 实现类需要在构造方法中传入监听参数
	 *
	 * @param transactionListener 事务回查实现类 *.java implements TransactionListener
	 * @param group 每一个Group会对应一个自己的实例对象，包裹在自己的单例中对外调用
	 * @author Yangcl
	 * @date 2019年6月18日 上午11:45:29 
	 * @version 1.0.0.1
	 */
	public BaseTransactionMqProducer(TransactionListener transactionListener , GttEnum group) {
		ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
	        @Override
	        public Thread newThread(Runnable r) {
	            Thread thread = new Thread(r);
	            thread.setName("client-transaction-msg-check-thread");
	            return thread;
	        }
	    });
		producer = new TransactionMQProducer(group.toString()); 
        producer.setExecutorService(executorService);
        producer.setTransactionListener(transactionListener); 
        producer.setNamesrvAddr( this.getConfig("matrix-rocket-mq.namesrv_" + this.getConfig("matrix-core.model")) );
		try {
			this.producer.start();
		} catch (MQClientException e) { 
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @description: 执行消息发送的方法
	 *
	 * @author Yangcl
	 * @date 2019年5月16日 下午2:14:29 
	 * @version 1.0.0.1
	 */
	public JSONObject sendMsg(Message msg ,Object arg) {
		JSONObject result = new JSONObject();
		result.put("status", "success");
		result.put("msg", this.getInfo(109010001));  // 109010001=消息发送成功!
		try {
			if(msg == null) {
				result.put("status", "error");
				result.put("msg", this.getInfo(109010007));  // 109010007=消息发送失败! Message消息体为空。
				return result;
			}
			sendResult = producer.sendMessageInTransaction(msg, arg);
		} catch (MQClientException e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(109010002));  // 109010002=同步消息发送失败，系统抛出异常!
		}finally {
//			producer.shutdown();   producer需要包裹在单例类中，不主动关闭。
		}
		
		if(sendResult == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(109010003));  // 109010003=异步消息发送失败，系统抛出异常! 请查看日志!
			return result;
		}
		
		result.put("data", sendResult);
		// TODO 此处泛化调用(非IP直连方式)Dubbo日志服务，记录最终执行结果。
		
		return result;
	}
	
	/**
	 * @description: 配置消息队列的Topic和Tag。
	 *
	 * @param topic 	  RocketMq主题
	 * @param tag		  RocketMq标签
	 * @param json 	  RocketMq要发送的消息体
	 * 
	 * @author Yangcl
	 * @date 2019年5月18日 下午12:53:35 
	 * @version 1.0.0.1
	 */
	public Message initMqMessage(String topic , String tag , String json) {
		byte[] bytes = null;
		try {
			bytes = json.getBytes(RemotingHelper.DEFAULT_CHARSET);  // UTF-8
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace();
			return null;
		}
		return new Message(topic, tag ,bytes);
	}
	
	/**
	 * @description: 子类获取该对象发送消息
	 *
	 * @author Yangcl
	 * @date 2019年5月15日 下午5:00:54 
	 * @version 1.0.0.1
	 */
	public DefaultMQProducer getProducer() {
		return producer;
	}
	
}








