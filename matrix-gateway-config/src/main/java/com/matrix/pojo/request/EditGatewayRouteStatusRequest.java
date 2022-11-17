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
public class EditGatewayRouteStatusRequest extends IdempotentRequest implements Serializable{

	private static final long serialVersionUID = 8802745158028157886L;

	@NotBlank(message = "106010002")		// 106010002=请求参数：路由规则ID(routeId)不得为空
	private String routeId; 
    
	private McUserInfoView userCache;
	
	
	public GwRoute buildEnableRequest() {  // 开启
		GwRoute e = new GwRoute();
		e.setRouteId(routeId);
		e.setStatus(1);													// 此条规则是否生效：0-不生效 1-生效；默认不生效，生效需要在列表触发
		e.buildUpdateCommon(userCache);
		return e;
	}
	
	public GwRoute buildResumePause() {	// 暂停
		GwRoute e = new GwRoute();
		e.setRouteId(routeId);
		e.setStatus(0);
		e.buildUpdateCommon(userCache);
		return e;
	}
}























































