package com.matrix.service;

import java.util.List;

import com.matrix.pojo.dto.ApplicationDto;
import com.matrix.pojo.view.ConsumerView;

public interface IAdminConsumerService {

	/**
	 * @description: 查询某个应用提供的所有消费信息
	 *
	 * @param dto.application 	服务名称
	 * @author Yangcl
	 * @date 2018年8月29日 下午5:15:52 
	 * @version 1.0.0.1
	 */
	public List<ConsumerView> listConsumerByApplication(ApplicationDto dto);

	/**
	 * @description: as the function name...
	 *
	 * @param conditions 成对出现，你懂的
	 * 
	 * @author Yangcl
	 * @date 2018年9月3日 下午4:14:07 
	 * @version 1.0.0.1
	 */
	public List<ConsumerView> listConsumerByConditions(String... conditions);
}
