package com.matrix.example;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.redisson.api.RLock;

import com.matrix.base.BaseMqPushConsumer;
import com.matrix.support.RedissonLock;


/**
 * @description: 顺序消费者使用示例。如果需要使用顺序消费，需要写msgProcessorOrderly()方法。
 * 
 * 	如何使用：ConsumerInit.java在项目启动时调用消费者
 * 					GttDto dto = new GttDto();
						dto.setConsumerType("");
						dto.setGroup("");
						dto.setTopic("");
						dto.setTag("");
						IRocketConsumer<DefaultMQPushConsumer> consumer = new AsyncSyncConsumer();
						consumer.doExecute(dto);
						
						for(MessageExt msg : msgs) {
							byte[] body = msg.getBody();
							try {
								String str = new String(body, "UTF-8");                          -------------------------------->   此处必须这么写，否则乱码！！！！！！！
								System.out.println("Consumer running = " + str);
								
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
						}
 *
 * @author Yangcl
 * @date 2019年5月21日 下午7:25:28 
 * @version 1.0.0.1
 */
public class SyncConsumerBhx extends BaseMqPushConsumer{

//	@Inject																 // 注入你需要的服务、mapper等资源	
//	private IShopInfoService shopInfoService;
	
	@Override
	public ConsumeConcurrentlyStatus msgProcessor(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		for(MessageExt msg : msgs) {
			System.err.println("UP = " +msg.getUserProperty("up") + " 开始添加分布式锁锁 MsgId = " + msg.getMsgId()); 
			RLock disLock = RedissonLock.getInstance().getRedissonClient().getLock(msg.getMsgId());
    		try {
    		    // 尝试获取分布式锁|第一个参数是等待时间，10秒内获取不到锁，则直接返回。 第二个参数 120是120秒后强制释放
    			boolean isLock = disLock.tryLock(5L, 20L, TimeUnit.SECONDS);
    		    if (isLock) {
    		    	// TODO 此处需要结合Redis做判断，下面的方式不可用
    		    	if(msg.getProperty("up").equals("user-property-2")) {
    		    		System.err.println("【非顺序消费】-----> " + msg.getUserProperty("up") + " trans = unknow");
//    					return ConsumeConcurrentlyStatus.RECONSUME_LATER;
    				}
    				byte[] body = msg.getBody();
    				String str = new String(body, "UTF-8");
    				System.err.println("【非顺序消费】成功 = " + str + " property = " + msg.getProperty("up"));
    		    }
    		} catch (UnsupportedEncodingException | InterruptedException  e) {
    			e.printStackTrace(); 
    		} finally {   // 无论如何, 最后都要解锁
    			System.err.println("MsgId = " + msg.getUserProperty("up") + " 释放锁"); 
    		    disLock.unlock();
    		}
		}
		
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;  // ConsumeConcurrentlyStatus.CONSUME_SUCCESS
	}

	@Override
	public ConsumeOrderlyStatus msgProcessorOrderly(List<MessageExt> msgs, ConsumeOrderlyContext context) {
		
		// 设置自动提交
		context.setAutoCommit(true);
		for(MessageExt msg : msgs) {
			byte[] body = msg.getBody();
			try {
				String str = new String(body, "UTF-8");
				System.err.println("【顺序消费】 = " + str);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return ConsumeOrderlyStatus.SUCCESS;
	}
	
}























