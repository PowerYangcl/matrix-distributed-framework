package com.matrix.service;

import java.util.List;

import com.matrix.pojo.view.ProviderView;

public interface IAdminProviderService {
	
	/**
	 * @description: 查询某个应用提供的所有服务信息
	 *
	 * @param dto.application 	服务名称
	 * @author Yangcl
	 * @date 2018年8月29日 下午5:15:52 
	 * @version 1.0.0.1
	 */
    public List<ProviderView> listProviderByApplication(String application);
    
    
    /**
     * @description: list provider by conditions
     *
     * @param conditions
     * @author Yangcl
     * @date 2018年9月3日 下午2:14:20 
     * @version 1.0.0.1
     */
    public List<ProviderView> listProviderByConditions(String... conditions);


    
    /**
     * @description: find provider by the type of long id
     *
     * @param id
     * @author Yangcl
     * @date 2018年9月3日 下午4:00:26 
     * @version 1.0.0.1
     */
	public ProviderView getProviderById(Long id);
}



















































