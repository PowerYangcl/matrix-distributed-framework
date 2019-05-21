package com.matrix.mq;

import java.util.List;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import com.matrix.base.BaseMqPushConsumer;


/**
 * @description: 非顺序消费者使用示例。如果需要使用顺序消费，需要写msgProcessorOrderly()方法。
 * 
 * 	如何使用：
 * 			JSONObject result = new AsyncSyncConsumer().doExecute(ConsumerType.Concurrently , 
 * 																														   	GttEnum.GroupTest, 
 * 																															GttEnum.TopicTest, 
 * 																															GttEnum.TagTest);
 *
 * @author Yangcl
 * @date 2019年5月21日 下午7:25:28 
 * @version 1.0.0.1
 */
public class AsyncSyncConsumer extends BaseMqPushConsumer{

//	@Inject																 // 注入你需要的服务、mapper等资源	
//	private IShopInfoService shopInfoService;
	
	
	@Override
	public ConsumeConcurrentlyStatus msgProcessor(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		for(Message msg : msgs){  
            System.out.println(new String(msg.getBody()));           
        }     
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

	@Override
	public ConsumeOrderlyStatus msgProcessorOrderly(List<MessageExt> msgs, ConsumeOrderlyContext context) {
		// TODO Auto-generated method stub
		return ConsumeOrderlyStatus.SUCCESS;
	}
	
	
}























