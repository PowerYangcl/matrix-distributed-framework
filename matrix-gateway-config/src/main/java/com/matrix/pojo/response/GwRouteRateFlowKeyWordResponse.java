package com.matrix.pojo.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GwRouteRateFlowKeyWordResponse {
	private static final long serialVersionUID = 2682519132645430553L;

	private Long id;
    private String name;
    private String type;
    private String target;
    private String param;
    private String value;
    private String statisticalDimension;
    
}
















































