package com.matrix.bhx;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseMqProducer;
import com.matrix.base.GttEnum;


/**
 * @description: 发送同步消息 或 单向消息
 * 	这种可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知。
 * 
 * 	如何使用：
 * 			JSONObject result = new SyncProducer(i).sendMsg();
 *
 * @author Yangcl
 * @date 2019年5月16日 上午10:56:23 
 * @version 1.0.0.1
 */
public class SyncProducerBhx extends BaseMqProducer{
	
	private Integer i;
	
	public SyncProducerBhx(Integer i) {
		this.i = i;
	}


	@Override
	public SendResult assemblyMsg() throws Exception {
		
		SendResult sendResult = null;
		
		// 准备发送消息
		try {
			Date date = new Date();
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	String dateStr = sdf.format(date);
        	
    		String body = dateStr + " 订单编号-1 " + i;
    		super.setKeys("rocketmq-keys");
    		Message msg = super.initMqConfig(
    				GttEnum.GroupOrderProducer, 	/* Group分组 */
    				GttEnum.TopicOrder,							/* Topic */
    				GttEnum.TagOrder, 							/* Tag */  
    				body);															/* Message body */
    		
    		sendResult = super.getProductor().send(
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
    		System.out.println("Producer -----> body：" + body);
    		
    		
    		
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        	
        }
		
		return sendResult;
	}
	

}



















































