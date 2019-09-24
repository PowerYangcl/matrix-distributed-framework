package com.matrix.base;

import java.io.UnsupportedEncodingException;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import com.matrix.gtt.GttEnum;

/**
 * @description: RockMq【非事务消息】顶层提供者，封装基础行为。
 * 	代码存放位置：约定在每个具体Dubbo服务的com.matrix.mq包下
 * 	使用原则：消息队列的使用不与业务代码强耦合
 * 	通常来讲，这个单例类应该写在每一个具体的业务项目中。
 * 
 * 	使用示例请参考：com.matrix.rocket.GroupDefaultTestSupport.java
 * 									com.matrix.service.impl.ExampleServiceImpl.ajaxRocketmqProducerInit()
 * 
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年4月24日 下午4:59:02 
 * @version 1.0.0.1
 */
public class BaseMqProducer extends BaseClass{

	private DefaultMQProducer producer = null;
	
	/**
	 * @description: 初始化Producer
	 *
	 * @param group 每一个Group会对应一个自己的实例对象，包裹在自己的单例中对外调用
	 * @author Yangcl
	 * @date 2019年6月27日 下午2:38:39 
	 * @version 1.0.0.1
	 */
	public BaseMqProducer(GttEnum group) {
		try {
			producer = new DefaultMQProducer(group.toString());
			producer.setNamesrvAddr( this.getConfig("matrix-rocket-mq.namesrv_" + this.getConfig("matrix-core.model")) );
			// 设置重试次数,默认2
            producer.setRetryTimesWhenSendFailed(15);
            //设置发送超时时间，默认是3000
            producer.setSendMsgTimeout(6000);
			this.producer.start();
		} catch (MQClientException e) { 
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @description: 配置消息队列的Topic和Tag。
	 *
	 * @param topic 	  RocketMq主题
	 * @param tag		  RocketMq标签
	 * @param json 	  RocketMq要发送的消息体
	 * 
	 * @author Yangcl
	 * @date 2019年5月18日 下午12:53:35 
	 * @version 1.0.0.1
	 */
	public Message initMqMessage(String topic , String tag , String json) {
		byte[] bytes = null;
		try {
			bytes = json.getBytes(RemotingHelper.DEFAULT_CHARSET);  // UTF-8
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace();
			return null;
		}
		return new Message(topic, tag ,bytes);
	}


	/**
	 * @description: 子类获取该对象发送消息
	 *
	 * @author Yangcl
	 * @date 2019年5月15日 下午5:00:54 
	 * @version 1.0.0.1
	 */
	public DefaultMQProducer getDefaultMQProducer() {
		return producer;
	}

}











































