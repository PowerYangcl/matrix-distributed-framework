package com.matrix.dao;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.GwRouteDto;
import com.matrix.pojo.entity.GwRoute;
import com.matrix.pojo.view.GwRouteView;

public interface IGwRouteMapper extends IBaseDao<Long, GwRoute, GwRouteDto, GwRouteView>{
    
	/**
	 * @description: 根据RouteId来更新数据记录
	 * 
	 * @author Yangcl
	 * @date 2022-11-16 18:04:01
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.1.4-spring-cloud-gateway
	 */
	public Integer updateByRouteId(GwRoute entity);
	
	/**
	 * @description: 根据RouteId来软删除数据记录
	 * 
	 * @author Yangcl
	 * @date 2022-11-16 18:04:17
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.1.4-spring-cloud-gateway
	 */
	public Integer deleteByRouteId(GwRoute entity);
}