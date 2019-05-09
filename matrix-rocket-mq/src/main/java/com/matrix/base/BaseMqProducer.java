package com.matrix.base;

import org.apache.rocketmq.client.producer.DefaultMQProducer;

/**
 * @description: RockMq顶层提供者，封装基础行为。
 *
 * @author Yangcl
 * @date 2016年4月24日 下午4:59:02 
 * @version 1.0.0.1
 */
public abstract class BaseMqProducer<T extends DefaultMQProducer>  extends BaseClass{

	private String group = "";   // 组
	private String topic = "";	// 主题
	private String nameServer = "";  // 
	private T producer;
	
	
	public BaseMqProducer() {
		producer.setProducerGroup(this.group);
		producer.setNamesrvAddr( this.getConfig("matrix-rocket-mq.namesrv_" + this.getConfig("matrix-core.model")) );
	}
	
	
	public String getGroup() {
		return group;
	}
	public BaseMqProducer<T> setGroup(String group) {
		this.group = group;
		return this;
	}
	public String getTopic() {
		return topic;
	}
	public BaseMqProducer<T> setTopic(String topic) {
		this.topic = topic;
		return this;
	}
	public String getNameServer() {
		return nameServer;
	}
	public BaseMqProducer<T> setNameServer(String nameServer) {
		this.nameServer = nameServer;
		return this;
	}
	public T getProducer() {
		return producer;
	}
	public BaseMqProducer<T> setProducer(T producer) {
		this.producer = producer;
		return this;
	} 
}
















































