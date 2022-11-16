package com.matrix.dao;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.GwRoutePredicatesDto;
import com.matrix.pojo.entity.GwRoutePredicates;
import com.matrix.pojo.view.GwRoutePredicatesView;

public interface IGwRoutePredicatesMapper  extends IBaseDao<Long, GwRoutePredicates, GwRoutePredicatesDto, GwRoutePredicatesView>{
	
	/**
	 * @description: 根据RouteId来软删除数据记录
	 * 
	 * @author Yangcl
	 * @date 2022-11-16 18:04:17
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.1.4-spring-cloud-gateway
	 */
	public Integer deleteByRouteId(GwRoutePredicates entity);
}