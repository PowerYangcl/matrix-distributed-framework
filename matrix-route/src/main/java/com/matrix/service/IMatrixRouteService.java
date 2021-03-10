package com.matrix.service;

import com.alibaba.fastjson.JSONObject;
import com.matrix.pojo.dto.PowerCacheDto;

public interface IMatrixRouteService {

	/**
	 * @description: 找到一个指定的一级缓存
	 * 
	 * @param dto.dubboAddr：10.12.52.29:20883
	 * @param dto.cacheName
	 * @param dto.key
	 * 
	 * @return {"msg":"操作成功","code":200,"data":"172.22.134.33","class":"com.matrix.base.RpcResult","status":"success"}
	 * @author Yangcl
	 * @date 2019年1月4日 下午2:40:33 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxRouteFindPowerCache(PowerCacheDto dto);

	/**
	 * @description: 查找多个服务节点中的缓存信息
	 *
 	 * @param dto.dubboAddr：10.12.52.28:20882,10.12.52.29:20883
	 * @param dto.cacheName
	 * @param dto.key
	 * 
	 * @author Yangcl
	 * @date 2019年1月9日 下午5:53:44 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxRouteFindAll(PowerCacheDto dto);
	
	/**
	 * @description: 添加一个或多个一级缓存到指定的节点 
	 *
	 * @param dto.dubboAddr：10.12.52.28:20882,10.12.52.29:20883
	 * @param dto.cacheName
	 * @param dto.key
	 * @param dto.value
	 * @param dto.batch 应对批量更新，key:value值，逗号分隔
	 * 
	 * @author Yangcl
	 * @date 2019年1月4日 下午4:59:44 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxRouteAddPowerCache(PowerCacheDto dto);

	/**
	 * @description: 更新一个或多个一级缓存到指定的节点 
	 *
	 * @param dto.dubboAddr：10.12.52.28:20882,10.12.52.29:20883
	 * @param dto.cacheName
	 * @param dto.key
	 * @param dto.value
	 * @param dto.batch 应对批量更新，key:value值，逗号分隔
	 * 
	 * @author Yangcl
	 * @date 2019年1月4日 下午4:59:44 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxRouteUpdatePowerCache(PowerCacheDto dto);

	/**
	 * @description: 删除一个或多个一级缓存到指定的节点 
	 *
	 * @param dto.dubboAddr：10.12.52.28:20882,10.12.52.29:20883
	 * @param dto.cacheName
	 * @param dto.key
	 * @param dto.value
	 * @param dto.batch 应对批量更新，key:value值，逗号分隔
	 * 
	 * @author Yangcl
	 * @date 2019年1月4日 下午4:59:44 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxRouteRemovePowerCache(PowerCacheDto dto);

	public JSONObject ajaxRouteExecute(PowerCacheDto dto);

	/**
	 * @description: 服务器文件列表
 	 *			需要批量设置：matrix-core.guard_file = com.matrix.security.FileGuard
	 *
	 * @param dto.dubboAddr
	 * @param dto.value
	 * 
	 * @author Yangcl
	 * @date 2019年1月24日 下午2:26:15 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxRouteDubboLogger(PowerCacheDto dto);

	/**
	 * @description: 服务器文件列表
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2019年1月24日 下午2:26:15 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxRouteServiceFiles(PowerCacheDto dto);


}

















































