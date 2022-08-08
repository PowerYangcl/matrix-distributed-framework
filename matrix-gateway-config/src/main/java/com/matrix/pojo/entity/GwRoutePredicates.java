package com.matrix.pojo.entity;


import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GwRoutePredicates extends BaseEntity{
	
	private static final long serialVersionUID = 2226204659522196543L;
	
	private Long id;
    private String routeId;
    private String predicate;
    private String predicateValue;

}