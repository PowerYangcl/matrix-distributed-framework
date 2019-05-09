package com.matrix.service;

import java.util.List;

import com.matrix.pojo.view.OverrideView;
import com.matrix.pojo.view.ProviderView;

public interface IAdminOverrideService {

	
	public List<OverrideView> listByProvider(ProviderView provider);
	
	/**
	 * @description: 获取dubbo提供者的 RPC接口信息概览
	 *
	 * @param provider
	 * @author Yangcl
	 * @date 2018年8月30日 下午4:00:25 
	 * @version 1.0.0.1
	 */
	public ProviderView configProvider(ProviderView provider); 

}
