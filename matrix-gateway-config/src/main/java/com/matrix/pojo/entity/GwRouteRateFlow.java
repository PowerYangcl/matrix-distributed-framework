package com.matrix.pojo.entity;


import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GwRouteRateFlow extends BaseEntity{
	
	private static final long serialVersionUID = 2681114325789945405L;

	private Long id;
    private String routeId;
    private Long keyWordId;
    private String redisKey;
    private Integer rate;

}