package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.matrix.aspectj.IdempotentRequest;
import com.matrix.pojo.entity.GwRoute;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeleteGatewayRouteRequest extends IdempotentRequest implements Serializable{

	private static final long serialVersionUID = 8800745158048157886L;

	@NotBlank(message = "106010002")		// 106010002=请求参数：路由规则ID(routeId)不得为空
	private String routeId; 
    
	private McUserInfoView userCache;
	
	
	public GwRoute buildGatewayRouteDeleteRequest() {
		GwRoute e = new GwRoute();
		e.setRouteId(routeId);
		e.buildUpdateCommon(userCache);
		return e;
	}
}























































