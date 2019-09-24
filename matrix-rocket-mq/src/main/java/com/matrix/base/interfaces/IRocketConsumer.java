package com.matrix.base.interfaces;


import com.alibaba.fastjson.JSONObject;
import com.matrix.gtt.GttDto;

public interface IRocketConsumer <T>{ 

	/**
	 * @description: 配置消费者
	 *
	 * @param dto.type 消费者类型：Concurrently 非顺序消费 | Orderly 顺序消费
	 * @param dto.group
	 * @param dto.topic
	 * @param dto.tag
	 * 
	 * @author Yangcl
	 * @home https://github.com/PowerYangcl
	 * @date 2019年5月20日 下午9:58:37 
	 * @version 1.0.0.1
	 */
	public JSONObject doExecute(GttDto dto);
	
	public T getConsumer();
	
	public void shutdown();
	
	public void resume();
}
