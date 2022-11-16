package com.matrix.pojo.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GwRoutePredicateResponse {
	private static final long serialVersionUID = 2682519132645430552L;

	private Long id;
	private String predicate;
	private String predicateValue;
}
















































