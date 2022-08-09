package com.matrix.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.matrix.base.BaseController;
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
	
	private static Logger logger = Logger.getLogger(GatewayConfigController.class);
	
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
		super.userBehavior(session, logger, "page_gateway_route_list", "前往网关路由规则列表");
		return "views/gateway/route/gateway-route-list";
	}
	
	/**
	 * @description: ac_api_project 列表数据信息
	 *
	 * @param entity
	 * @param session
	 * @author Yangcl
	 * @date 2017年11月14日 上午9:38:58 
	 * @version 1.0.0
	 */
//	@RequestMapping(value = "ajax_api_project_list", produces = { "application/json;charset=utf-8" })
//	@ResponseBody
//	public Result<PageInfo<AcApiProjectView>> ajaxApiProjectList(FindApiProjectListRequest param , HttpServletRequest request, HttpSession session){ 
//		super.userBehavior(session, logger, "ajax_api_project_list", "获取api所属项目列表页数据");
//		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
//		return service.ajaxApiProjectList(param, request);  
//	}
	
}









































