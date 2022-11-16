package com.matrix.dao;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.GwRouteRateFlowKeyWordsDto;
import com.matrix.pojo.entity.GwRouteRateFlowKeyWords;
import com.matrix.pojo.view.GwRouteRateFlowKeyWordsView;

public interface IGwRouteRateFlowKeyWordsMapper  extends IBaseDao<Long, GwRouteRateFlowKeyWords, GwRouteRateFlowKeyWordsDto, GwRouteRateFlowKeyWordsView>{

	/**
	 * @description: 根据RouteId来软删除数据记录
	 * 
	 * @author Yangcl
	 * @date 2022-11-16 18:04:17
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.1.4-spring-cloud-gateway
	 */
	public Integer deleteByRouteId(GwRouteRateFlowKeyWords entity);
}