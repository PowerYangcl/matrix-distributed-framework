package com.matrix.example;

import java.io.UnsupportedEncodingException;
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
 * 					GttDto dto = new GttDto();
						dto.setConsumerType("");
						dto.setGroup("");
						dto.setTopic("");
						dto.setTag("");
						IRocketConsumer<DefaultMQPushConsumer> consumer = new AsyncSyncConsumer();
						consumer.doExecute(dto);
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
		System.out.println("开始进行【顺序消费】");
		
		// 设置自动提交
		context.setAutoCommit(true);
		for(MessageExt msg : msgs) {
			byte[] body = msg.getBody();
			try {
				String str = new String(body, "UTF-8");
				System.out.println("Consumer running = " + str);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return ConsumeOrderlyStatus.SUCCESS;
	}
	
}























