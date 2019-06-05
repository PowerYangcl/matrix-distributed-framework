package com.matrix.bhx;

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

	
	@Override
	public SendResult assemblyMsg() throws Exception {
		
		String productInfo = "Hello RocketMQ";    
		super.setKeys("rocketmq-keys");
		Message msg = super.initMqConfig(
				GttEnum.GroupOrderProducer, 	/* Group分组 */
				GttEnum.TopicOrder,		/* Topic */
				GttEnum.TagOrder, 		/* Tag */  
				productInfo);												/* Message body */
		
		if(msg == null) {
			return null;     // throw new Exception(); 或尝试其他操作，请根据具体业务场景来确定
		}
		
		// 准备发送消息
		
		return null;
	}
	

}



















































