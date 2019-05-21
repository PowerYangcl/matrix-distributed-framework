package com.matrix.mq;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import com.matrix.base.BaseMqProducer;
import com.matrix.base.GttEnum;


/**
 * @description: 发送异步消息
 * 	异步消息通常用在对响应时间敏感的业务场景，即发送端不能容忍长时间地等待Broker的响应。
 * 
 * 	如何使用：
 * 			JSONObject result = new AsyncProducer().sendMsg();
 *
 * @author Yangcl
 * @date 2019年5月16日 上午10:56:23 
 * @version 1.0.0.1
 */
public class AsyncProducer extends BaseMqProducer{


//	@Inject																 // 注入你需要的服务、mapper等资源	
//	private IShopInfoService shopInfoService;
	
	private  SendResult result = null;
	
	@Override
	public SendResult assemblyMsg() throws Exception {
		
		String productInfo = "Hello RocketMQ";    // TODO 此处消息主要来源于对象的JSON序列字符串，由具体业务
		
		// 开始配置消息队列的Group、Topic和Tag。
		this.getProductor().setProducerGroup(GttEnum.GroupTest.toString());
		
		// 异步发送消息，发送失败时再尝试发送次数 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		this.getProductor().setRetryTimesWhenSendAsyncFailed(20);       
		
		/**
		 * 警告！！！！
		 * 		禁止开发人员直接操作DefaultMQProducer.java中的setProducerGroup()方法来设置分组名称；
		 * 		分组名称、主题和标签必须由GroupTopicTagEnum类统一约束，违反约定者重罚。
		 */
		Message msg = super.initMqConfig(
				GttEnum.GroupTest, 	/* Group分组 */
				GttEnum.TopicTest,		/* Topic */
				GttEnum.TagTest, 		/* Tag */  
				productInfo);												/* Message body */
		
		if(msg == null) {
			// TODO   父类会出现UnsupportedEncodingException，导致返回Message对象为空
			// throw new Exception();
			return null;     // throw new Exception(); 或尝试其他操作，请根据具体业务场景来确定
		}
		
		// 准备发送消息
		super.getProductor().send(msg, 
				new SendCallback() {
		            public void onSuccess(SendResult sendResult) {
		            	result = sendResult; 
		            	// TODO 你想做的其他操作，根据业务需求来定
		            }
		            public void onException(Throwable e) {
			              e.printStackTrace();
			           // TODO 你想做的其他操作，根据业务需求来定
		            }
		    	}, 
				60*1000
			);
		
		return result;
	}
	

}



















































