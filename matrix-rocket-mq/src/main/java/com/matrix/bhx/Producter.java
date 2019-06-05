package com.matrix.bhx;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class Producter {

    public static void main(String[] args) throws MQClientException, InterruptedException {

    	String groupName = "order-producer-group";
        DefaultMQProducer producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr("10.12.52.34:9876;10.12.52.35:9876");
        producer.start();
        

        try {
        	Date date = new Date();
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	String dateStr = sdf.format(date);
        	
        	for(int i = 1; i <= 5 ; i ++) {
        		String body = dateStr + " 订单编号-1 " + i;
        		Message msg = new Message("TopicOrder", "order_1", "key" + i , body.getBytes(RemotingHelper.DEFAULT_CHARSET)); 
        		
        		SendResult sendResult = producer.send(
    				msg, 
    				new MessageQueueSelector() { 
						@Override
						public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
							Integer id  = (Integer) arg;
							return mqs.get(id); 
						}
					}, 
    				0, 				// 队列的下标
    				60_000		// 超时时间
				);
        		System.out.println(sendResult + "-----> body：" + body);
        	}
        	
        	
        	for(int i = 1; i <= 5 ; i ++) {
        		String body = dateStr + " 订单编号-2 " + i;
        		Message msg = new Message("TopicOrder", "order_2", "key" + i , body.getBytes(RemotingHelper.DEFAULT_CHARSET));
        		
        		SendResult sendResult = producer.send(
    				msg, 
    				new MessageQueueSelector() { 
						@Override
						public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
							Integer id  = (Integer) arg;
							return mqs.get(id); 
						}
					}, 
    				0, 				// 队列的下标
    				60_000		// 超时时间
				);
        		System.out.println(sendResult + "-----> body：" + body);
        	}
        	
        	for(int i = 1; i <= 5 ; i ++) {
        		String body = dateStr + " 订单编号-3 " + i;
        		Message msg = new Message("TopicOrder", "order_3", "key" + i , body.getBytes(RemotingHelper.DEFAULT_CHARSET));
        		
        		SendResult sendResult = producer.send(
    				msg, 
    				new MessageQueueSelector() { 
						@Override
						public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
							Integer id  = (Integer) arg;
							return mqs.get(id); 
						}
					}, 
    				0, 				// 队列的下标
    				60_000		// 超时时间
				);
        		System.out.println(sendResult + "-----> body：" + body);
        	}
        	
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        	producer.shutdown();
        }
        
    }
}


























