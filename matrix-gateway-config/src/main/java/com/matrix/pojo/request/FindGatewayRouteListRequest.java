package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.base.BaseClass;
import com.matrix.pojo.dto.GwRouteDto;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FindGatewayRouteListRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 8800745158048157336L;

	private String routeId;
	
	private String active;		// 生产环境仅限于：dev|test|pre|master|prod
	
	private Integer rateFlowMark;		// 是否开启流量标记：0-不标记 1-标记
	
	private String description;		// 路由转发规则描述
    
	private McUserInfoView userCache;
	
	
	public GwRouteDto buildGatewayRouteListRequest() {
		GwRouteDto e = new GwRouteDto();
		e.setRouteId(routeId);
		e.setActive(active);
		e.setRateFlowMark(rateFlowMark);
		e.setDescription(description);
		return e;
	}
}


