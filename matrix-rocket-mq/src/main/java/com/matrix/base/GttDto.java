package com.matrix.base;

import java.io.Serializable;

/**
 * @description: Group Topic Tag Dto 数据传输模型
 *
 * @author Yangcl
 * @date 2019年6月3日 下午3:02:35 
 * @version 1.0.0.1
 */
public class GttDto implements Serializable {
	private static final long serialVersionUID = 225277774272495617L;
	private String group;
	private String topic;
	private String tag;
	private String consumerType;  //  消费者类型：Concurrently 非顺序消费 | Orderly 顺序消费
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getConsumerType() {
		return consumerType;
	}
	public void setConsumerType(String consumerType) {
		this.consumerType = consumerType;
	}
	
	
}
