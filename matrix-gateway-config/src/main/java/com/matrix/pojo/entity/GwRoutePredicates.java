package com.matrix.pojo.entity;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.matrix.base.BaseEntity;
import com.matrix.validate.Validation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GwRoutePredicates extends BaseEntity{
	
	private static final long serialVersionUID = 2226204659522196543L;
	
	private Long id;
    private String routeId;
    
	@NotBlank(message = "106010021")			// 106010021=断言规则不得为空
	@Pattern(regexp=Validation.GW_PREDICATES, message="106010022")			// 106010022=断言规则仅限于：Path|After|Before|Between|Cookie|Header|Host|Method|Query|RemoteAddr|Weight|XForwardedRemoteAddr
    private String predicate;
	
	@NotBlank(message = "106010023")			// 106010023=断言规则对应值不得为空
    private String predicateValue;

}