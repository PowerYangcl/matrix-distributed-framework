package com.matrix.rpc;

import com.matrix.base.Result;
import com.matrix.pojo.dto.PowerCacheDto;

public interface IMatrixRouteRpcService {

	/**
	 * @description: 添加一个一级缓存
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2018年11月20日 下午5:45:35 
	 * @version 1.0.0.1
	 */
	public Result<?> addPowerCache(PowerCacheDto dto);
	
	/**
	 * @description: 更新一级缓存
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2018年11月20日 下午5:46:01 
	 * @version 1.0.0.1
	 */
	public Result<?> updatePowerCache(PowerCacheDto dto);
	
	/**
	 * @description: 删除一级缓存
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2018年11月20日 下午5:46:30 
	 * @version 1.0.0.1
	 */
	public Result<?> removePowerCache(PowerCacheDto dto);
	
	/**
	 * @description: 查找一级缓存|如果没有找到对应的值则返回Null
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2018年11月20日 下午5:46:45 
	 * @version 1.0.0.1
	 */
	public Result<String> findPowerCache(PowerCacheDto dto);
	
	/**
	 * @description: 并行触发要执行的节点调用类
	 *
	 * @param dto.key invoke class path
	 * @param dto.value function parameters as json string
	 * @author Yangcl
	 * @date 2019年1月5日 上午9:31:12 
	 * @version 1.0.0.1
	 */
	public Result<String> routeExecute(PowerCacheDto dto);
	
}




































