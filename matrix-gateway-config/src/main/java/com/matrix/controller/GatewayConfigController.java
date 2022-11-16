package com.matrix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.matrix.aspectj.Idempotent;
import com.matrix.base.BaseController;
import com.matrix.base.Result;
import com.matrix.pojo.request.AddGatewayRouteRequest;
import com.matrix.pojo.request.FindGatewayRouteListRequest;
import com.matrix.pojo.response.GwRouteResponse;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IGatewayConfigService;

/**
 * @description: leader操作入口
 * 
 * @author Yangcl
 * @date 2022-8-8 17:43:37
 * @home https://github.com/PowerYangcl
 * @path matrix-gateway-config / com.matrix.controller.GatewayConfigController.java
 * @version 1.6.1.4-spring-cloud-gateway
 */
@Controller
@RequestMapping("gateway")
public class GatewayConfigController extends BaseController {
	
	@Autowired
	private IGatewayConfigService gatewayConfigService;  
	
	
	/**
	 * @description: 路由规则列表页JSP
	 * 
	 * @author Yangcl
	 * @date 2022-8-8 18:24:05
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.1.4-spring-cloud-gateway
	 */
	@RequestMapping("page_gateway_route_list")  
	public String apiProjectList(HttpSession session){ 
		super.userBehavior(session, "page_gateway_route_list", "前往网关路由规则列表");
		return "views/gateway/route/gateway-route-list";
	}
	
	/**
	 * @description: 添加一条网关路由规则，入参以对象方式发送(对象包含数组)
	 * 
	 * @author Yangcl
	 * @date 2022-8-15 15:34:02
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.1.4-spring-cloud-gateway
	 */
	@ResponseBody
	@Idempotent(accessToken = false)	
	@RequestMapping(value = "ajax_btn_gateway_route_add",method = {RequestMethod.POST}, produces = { "application/json;charset=utf-8" })
	public Result<?> ajaxBtnGatewayRouteAdd(@RequestBody @Validated AddGatewayRouteRequest param , HttpServletRequest request, HttpSession session){ 
		super.userBehavior(session, "ajax_btn_gateway_route_add", "添加一条网关路由规则");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return gatewayConfigService.ajaxBtnGatewayRouteAdd(param, request);  
	}
	
	/**
	 * @description: 网关路由规则列表
	 * 
	 * @author Yangcl
	 * @date 2022-8-18 14:35:45
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.1.4-spring-cloud-gateway
	 */
	@ResponseBody
	@Idempotent(accessToken = false)	
	@RequestMapping(value = "ajax_gateway_route_list.do", produces = { "application/json;charset=utf-8" })
	public Result<PageInfo<GwRouteResponse>> ajaxGatewayRouteList(FindGatewayRouteListRequest param , HttpServletRequest request, HttpSession session){ 
		super.userBehavior(session, "ajax_gateway_route_list", "网关路由规则列表");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return gatewayConfigService.ajaxGatewayRouteList(param, request);  
	}
}









































