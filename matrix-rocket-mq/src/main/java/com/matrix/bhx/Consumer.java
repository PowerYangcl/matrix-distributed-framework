package com.matrix.bhx;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

public class Consumer {

    public static void main(String[] args) throws InterruptedException, MQClientException {
    	
    	
    	
    	
    	String groupName = "order-producer-group";

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr("10.12.52.34:9876;10.12.52.35:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("TopicOrder", "*"); 
        consumer.registerMessageListener(new Orderlylistener());
        

        consumer.start();
        System.out.printf("Consumer Started.");
    }
}


class Orderlylistener implements MessageListenerOrderly{
	
	private Random random = new Random();

	@Override
	public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
		// 设置自动提交
		context.setAutoCommit(true);
		for(MessageExt msg : msgs) {
			System.out.println(new String(msg.getBody()));
		}
		
		try {
			TimeUnit.SECONDS.sleep(random.nextInt(1));
		} catch (InterruptedException e) {
			e.printStackTrace();
			return 	ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
		}
		
		
		
		return ConsumeOrderlyStatus.SUCCESS;
	}
	
}

































