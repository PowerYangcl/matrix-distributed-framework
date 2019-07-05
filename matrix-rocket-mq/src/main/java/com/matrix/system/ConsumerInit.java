package com.matrix.system;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseInit;
import com.matrix.base.GttDto;
import com.matrix.base.interfaces.IRocketConsumer;
import com.matrix.example.AsyncSyncConsumer;
import com.matrix.example.SyncConsumerBhx;

public class ConsumerInit  extends BaseInit{

	@Override
	public boolean onInit() {
		
		
		GttDto dto = new GttDto();
//		dto.setConsumerType("Concurrently");
//		dto.setGroup("GroupTransTest");
		
		dto.setConsumerType("Orderly");
		dto.setGroup("GroupDefaultTest");
		
		dto.setTopic("TopicOrder");
		dto.setTag("TagOrder");
		IRocketConsumer<DefaultMQPushConsumer> consumer = new SyncConsumerBhx();
		JSONObject doExecute = consumer.doExecute(dto);
		
		
//		IRocketConsumer<DefaultMQPushConsumer> consumer2 = new AsyncSyncConsumer(); 
//		JSONObject doExecute2 = consumer2.doExecute(dto);
		
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Consumer Init"); 
		
		return true;
	}

	@Override
	public boolean onDestory() {
		// TODO Auto-generated method stub
		return false;
	}

}
