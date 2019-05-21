package com.matrix.mq;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import com.matrix.base.BaseMqProducer;
import com.matrix.base.GttEnum;


/**
 * @description: 发送同步消息 或 单向消息
 * 	这种可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知。
 * 
 * 	如何使用：
 * 			JSONObject result = new SyncProducer().sendMsg();
 *
 * @author Yangcl
 * @date 2019年5月16日 上午10:56:23 
 * @version 1.0.0.1
 */
public class SyncProducer extends BaseMqProducer{

// 					注入你需要的服务、mapper等资源	
//	@Inject
//	private IShopInfoService shopInfoService;
	
	@Override
	public SendResult assemblyMsg() throws Exception {
		
		String productInfo = "Hello RocketMQ";    // 此处消息主要来源于对象的JSON序列字符串，由具体业务来定
		
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
		if(productInfo.length() < 2000) {
			return super.getProductor().send(msg , 60*1000);           // 开始发送同步消息
		}else {
			/**
			 *  单向发送消息
			 *  这种方式主要用在不特别关心发送结果的场景，例如日志发送。返回值为空。
			 */
			super.getProductor().sendOneway(msg);		
			return null;
		}
	}
	

}



















































