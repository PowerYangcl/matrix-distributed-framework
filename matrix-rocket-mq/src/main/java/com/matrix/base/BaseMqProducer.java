package com.matrix.base;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import com.alibaba.fastjson.JSONObject;
import com.matrix.util.ExceptionUtils;

/**
 * @description: RockMq顶层提供者，封装基础行为。
 * 	代码存放位置：约定在每个具体Dubbo服务的com.matrix.mq包下
 * 	使用原则：消息队列的使用不与业务代码强耦合
 * 
 * 	使用示例请参考：SyncProducer.java和AsyncProducer.java 
 * 
 * @author Yangcl
 * @date 2016年4月24日 下午4:59:02 
 * @version 1.0.0.1
 */
public abstract class BaseMqProducer extends BaseClass{

	private DefaultMQProducer producer = new DefaultMQProducer();
	private SendResult sendResult = null;
	
	private String keys = null;  // org.apache.rocketmq.common.message.Message.java默认参数，如果需要请自行设置值
	
	/**
	 * @description: 子类组装消息的方法
	 *
	 * @author Yangcl
	 * @date 2019年5月16日 上午10:56:55 
	 * @version 1.0.0.1
	 */
	public abstract SendResult assemblyMsg() throws Exception;
	
	/**
	 * @description: 执行消息发送的方法
	 *
	 * @author Yangcl
	 * @date 2019年5月16日 下午2:14:29 
	 * @version 1.0.0.1
	 */
	public JSONObject sendMsg() {
		JSONObject result = new JSONObject();
		result.put("status", "success");
		result.put("msg", this.getInfo(109010001));  // 109010001=消息发送成功!
		producer.setNamesrvAddr( this.getConfig("matrix-rocket-mq.namesrv_" + this.getConfig("matrix-core.model")) );
		try {
			producer.start();
			sendResult = this.assemblyMsg();
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(109010002));  // 109010002=同步消息发送失败，系统抛出异常!
			result.put("data", ExceptionUtils.getExceptionInfo(e));
		}finally {
			producer.shutdown();
		}
		
		if(sendResult == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(109010003));  // 109010003=异步消息发送失败，系统抛出异常! 请查看日志!
			return result;
		}
		
		result.put("data", sendResult);
		// TODO 此处泛化调用(非IP直连方式)Dubbo日志服务，记录最终执行结果。
		
		
		return result;
	}
	
	
	/**
	 * @description: 配置消息队列的Group、Topic和Tag。
	 * 	警告！！！！
	 * 			禁止开发人员直接操作DefaultMQProducer.java中的setProducerGroup()方法来设置分组名称；
	 * 			分组名称、主题和标签必须由GroupTopicTagEnum类统一约束，违反约定者重罚。
	 *
	 * @param group  RocketMq分组
	 * @param topic 	  RocketMq主题
	 * @param tag		  RocketMq标签
	 * @param json 	  RocketMq要发送的消息体
	 * 
	 * @author Yangcl
	 * @date 2019年5月18日 下午12:53:35 
	 * @version 1.0.0.1
	 */
	public Message initMqConfig(GttEnum group , GttEnum topic , GttEnum tag , String json) {
		this.producer.setProducerGroup(group.toString());
		byte[] bytes = null;
		try {
			bytes = json.getBytes(RemotingHelper.DEFAULT_CHARSET);  // UTF-8
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		Message msg = null;
		
		if(StringUtils.isNotBlank(this.keys)) {
			msg = new Message(
					GttEnum.TopicTest.toString(),   		/* Topic */
					GttEnum.TagTest.toString() 		 		/* Tag */ ,   
					keys,
					bytes 															/* Message body */
			);
		}else {
			msg = new Message(
					GttEnum.TopicTest.toString(),   		/* Topic */
					GttEnum.TagTest.toString() 		 		/* Tag */ ,   
					bytes 															/* Message body */
			);
		}
		
		
		return msg;
	}
	
	
	/**
	 * @description: 子类获取该对象发送消息
	 *
	 * @author Yangcl
	 * @date 2019年5月15日 下午5:00:54 
	 * @version 1.0.0.1
	 */
	public DefaultMQProducer getProductor() {
		return producer;
	}
	
	public void setKeys(String keys) {
		this.keys = keys;
	}
}

