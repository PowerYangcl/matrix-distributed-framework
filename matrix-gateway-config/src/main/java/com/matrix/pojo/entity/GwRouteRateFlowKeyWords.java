package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GwRouteRateFlowKeyWords extends BaseEntity{
	
	private static final long serialVersionUID = 1677899529277610082L;

	private Long id;
    private String routeId;
    private String name;
    private String type;
    private String target;
    private String param;
    private String value;
    private String statisticalDimension;

}