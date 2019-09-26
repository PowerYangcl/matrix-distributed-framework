package com.matrix.system;

import com.matrix.base.BaseInit;
import com.matrix.gtt.GttDto;


/**
 * @description: 分布式消息队列初始化|通过初始化数据表中的消费者，根据IP地址分组，反射调用每一个消费者
 * 
 * 		关于重复消费问题：https://blog.csdn.net/a417930422/article/details/50663629
 * 											https://blog.csdn.net/wangsht/article/details/84776906
 * 	
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2019年9月24日 下午6:00:05 
 * @version 1.0.0.1
 */
public class ConsumerInit  extends BaseInit{

	@Override
	public boolean onInit() {
		GttDto dto = new GttDto();
		dto.setConsumerType("Orderly");
		dto.setGroup("MqGroupShopCustomerConsumerInfo");
		dto.setTopic("TopicShopCustomerConsumerInfo");
		dto.setTag("TagShopCustomerConsumerInfo");

		return true;
	}

	@Override
	public boolean onDestory() {
		// TODO Auto-generated method stub
		return false;
	}

}
