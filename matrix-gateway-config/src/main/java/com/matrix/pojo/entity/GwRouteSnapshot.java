package com.matrix.pojo.entity;


import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GwRouteSnapshot extends BaseEntity{
	
	private static final long serialVersionUID = 5821611305257490718L;

	private Long id;
    private Long routeIden;
    private String routeId;
    private String url;
    private String header;
    private String cookies;
    private String requestType;

}



