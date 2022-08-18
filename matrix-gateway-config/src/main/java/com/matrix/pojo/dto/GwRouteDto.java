package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GwRouteDto  extends BaseDto{
	private static final long serialVersionUID = 2282519132645430551L;

	private String routeId;
	
	private String active;		// 生产环境仅限于：dev|test|pre|master|prod
	
	private Integer rateFlowMark;		// 是否开启流量标记：0-不标记 1-标记
	
	private String description;		// 路由转发规则描述
}