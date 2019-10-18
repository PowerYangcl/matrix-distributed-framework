package com.matrix.base;

import java.util.List;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.interfaces.IRocketConsumer;
import com.matrix.gtt.GttDto;


/**
 * @description: RockMq顶层消费者；通常在系统中很少用到DefaultMQPullConsumer来主动拉去消息，
 * 	如果在业务中使用到它，请新建BaseMqPullConsumer.java类。此类会封装DefaultMQPushConsumer对象。
 * 
 * 
 * 	使用示例请参考：AsyncSyncConsumer.java
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年4月24日 下午4:59:02 
 * @version 1.0.0.1
 */
public abstract class BaseMqPushConsumer extends BaseClass implements IRocketConsumer<DefaultMQPushConsumer>{
	
	private DefaultMQPushConsumer consumer = null;
	
	/**
	 * @description: 【非顺序消费】 - 消息处理者，子类需要实现此方法 - 此方式系统吞吐量高|此方式常用
	 * 
	 * 		【注意】！需要在子类中添加分布式锁，防止重复消费；请参考： com.matrix.example.SyncConsumerBhx.java
	 * 
	 * 		msgs中只收集同一个topic，同一个tag，并且key相同的message   
	 * 		会把不同的消息分别放置到不同的队列中  
	 * 
	 * 	保留此处代码，仅做示例。
                    for(Message msg : msgs){  
                        System.out.println(new String(msg.getBody()));           
                    }     
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;  
	 * 
	 * @param msgs
	 * @param context
	 * @author Yangcl
	 * @date 2019年5月20日 下午9:57:29 
	 * @version 1.0.0.1
	 */
	public abstract ConsumeConcurrentlyStatus msgProcessor(List<MessageExt> msgs , ConsumeConcurrentlyContext context);
	
	
	/**
	 * @description: 【顺序消费】 - 消息处理者，子类需要实现此方法 - 此方式系统吞吐量相对较低
	 * 
	 * 		【注意】！需要在子类中添加分布式锁，防止重复消费；请参考： com.matrix.example.SyncConsumerBhx.java
	 * 		msgs中只收集同一个topic，同一个tag，并且key相同的message   
	 * 		会把不同的消息分别放置到不同的队列中  
	 * 
	 * 	保留此处代码，仅做示例。
                    for(Message msg : msgs){  
                        System.out.println(new String(msg.getBody()));           
                    }     
                    return ConsumeOrderlyStatus.SUCCESS;  
	 * 
	 * @param msgs
	 * @param context
	 * @author Yangcl
	 * @date 2019年5月20日 下午9:57:29 
	 * @version 1.0.0.1
	 */
	public abstract ConsumeOrderlyStatus msgProcessorOrderly(List<MessageExt> msgs , ConsumeOrderlyContext context);
	
	/**
	 * @description: 配置消费者
	 *
	 * @param dto.type 消费者类型：Concurrently 非顺序消费 | Orderly 顺序消费
	 * @param dto.group
	 * @param dto.topic
	 * @param dto.tag
	 * @author Yangcl
	 * @date 2019年5月20日 下午9:58:37 
	 * @version 1.0.0.1
	 */
	public JSONObject doExecute(GttDto dto) {
		JSONObject result = new JSONObject();
		result.put("status", "success");
		result.put("msg", this.getInfo(109010004));  // 109010004=消息处理成功!
		
		consumer = new DefaultMQPushConsumer(dto.getGroup());
		consumer.setNamesrvAddr(this.getConfig("matrix-rocket-mq.namesrv_" + this.getConfig("matrix-core.model")) );
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET); 	// 程序第一次启动从消息队列头获取数据  
        
        // TODO 此处值的设置有待研究，暂时采用默认值，可以通过页面配置，成为动态数据。
        // 消费线程池设置  
//        consumer.setConsumeThreadMin(10);  // 最小消费线程池数量：10(默认)
//        consumer.setConsumeThreadMax(20);  // 最大消费线程数量：20(默认)
        // 拉消息|本地队列缓存消息最大数：默认1000  
//        consumer.setPullThresholdForQueue(1000); 
        // 批量消费，一次消费多少条消息：默认1条
//        consumer.setConsumeMessageBatchMaxSize(1); 
        // 批量拉消息，一次最多拉取多少条，默认32条
//        consumer.setPullBatchSize(32);
        // 消息拉取线程每隔多久拉取一次，默认0
//        consumer.setPullInterval(0);
        if(dto.getMaxReconsumeTimes() != null) {  // 如果传递此参数，则设置为自定义的重试次数
        	consumer.setMaxReconsumeTimes(dto.getMaxReconsumeTimes());
        } 
        
		try {
			consumer.subscribe(dto.getTopic() , dto.getTag()); 		// 开始订阅消息，tag可以为"*"
			if(dto.getConsumerType().equals("Concurrently")) {  		// 注册消费的监听 - 非顺序消费
				consumer.registerMessageListener(new MessageListenerConcurrently(){  		  
	                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs , ConsumeConcurrentlyContext context) { 
	                	return msgProcessor(msgs, context);  
	                }  
	            }); 
			}else if(dto.getConsumerType().equals("Orderly")) {		// 注册消费的监听 - 顺序消费
				consumer.registerMessageListener(new MessageListenerOrderly() {				
					public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
						return msgProcessorOrderly(msgs, context);
					}
				});
			}else {
				result.put("status", "error");
				result.put("msg", this.getInfo(109010006));  // 109010006=消息处理失败，ConsumerType 存在非法的枚举类型
				return result;
			}
			consumer.start(); 
		} catch (MQClientException e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(109010005));  // 109010005=消息处理失败，系统抛出异常! BaseMqPushConsumer.java -> initConsumerConfig() Topic 或 Tag出现错误
			return result;
		}
		
		return result;
	}
	
	
	/**
	 * @description: 获取消费者，执行其他操作；
	 * 	比如设置广播消费模式：consumer.setMessageModel(MessageModel.BROADCASTING);
	 * 	挂载消费端：consumer.suspend();
	 *
	 * @author Yangcl
	 * @date 2019年5月20日 下午10:05:25 
	 * @version 1.0.0.1
	 */
	public DefaultMQPushConsumer getConsumer() {
		return consumer;
	}
	
	public void shutdown() {
		this.consumer.shutdown(); 
	}
	
	public void resume() {
		this.consumer.resume();
	}
}























