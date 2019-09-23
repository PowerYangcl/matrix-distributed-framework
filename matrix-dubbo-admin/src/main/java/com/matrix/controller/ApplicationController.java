package com.matrix.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import com.matrix.pojo.dto.AppProvideDto;
import com.matrix.pojo.dto.ApplicationDto;
import com.matrix.pojo.dto.RpcFunctionDto;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IAdminApplicationService;

/**
 * @description: dubbo 服务控制层
 *
 * @author Yangcl
 * @date 2018年8月27日 上午11:49:34 
 * @version 1.0.0.1
 */
@Controller
@RequestMapping("/application")
public class ApplicationController {

	@Autowired
	private IAdminApplicationService adminApplicationService;
	
	@RequestMapping("page_goto_sentinel_dashboard")   // 代码可能废弃
	public String pageGotoSentinelDashboard(ModelMap model ,HttpSession session){
        McUserInfoView info = (McUserInfoView) session.getAttribute("userInfo");
        model.put("userInfo", info);
		return "jsp/dubbo/sentinel/goto-sentinel-dashboard";
	}
	
	/**
	 * @description: Dubbo应用服务列表|Dubbo项目名称列表
	 *
	 * @param session
	 * @author Yangcl
	 * @date 2018年8月29日 下午3:06:09 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_application_dubbo_project_list")
	public String pageApplicationDubboProjectList(HttpSession session){
		return "jsp/dubbo/admin/dubbo-project-list";
	}
	
	/**
	 * @description:  查询所有应用列表信息
	 *			http://oms.ostar.com:8080/ostar-oms/application/ajaxFindApplicationList
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2018年8月27日 下午5:15:30 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajaxFindApplicationList", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxFindApplicationList(ApplicationDto dto) {
		return adminApplicationService.ajaxFindApplicationList(dto);
	}
	
	
	
	/**
	 * @description: Dubbo项目部署节点列表
	 *
	 * @param dto.application 	服务名称
	 * @author Yangcl
	 * @date 2018年8月29日 下午5:01:37 
	 * @version 1.0.0.1
	 */
	@RequestMapping("redirect_application_dubbo_project_ip_list")
	public String pageApplicationDubboIpList(ApplicationDto dto , ModelMap model ,HttpSession session){
		model.put("application" , dto.getApplication());
		return "jsp/dubbo/admin/dubbo-project-ip-list";
	}
	
	/**
	 * @description: Dubbo项目部署节点列表数据集合
	 *			http://oms.ostar.com:8080/ostar-oms/application/ajaxFindDubboProjectIpList?application=OSTAR_OMS_SERVICE
	 *
	 * @param dto.application 	服务名称
	 * @author Yangcl
	 * @date 2018年8月29日 下午5:08:51 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajaxFindDubboProjectIpList", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxFindDubboProjectIpList(ApplicationDto dto) {
		return adminApplicationService.ajaxFindDubboProjectIpList(dto);
	}
	
	
	
	/**
	 * @description: Dubbo项目指定部署节点下的RPC接口类列表集合|jsp页面地址
	 *
	 * @param dto
	 * @param model
	 * @author Yangcl
	 * @date 2018年8月30日 下午3:14:52 
	 * @version 1.0.0.1
	 */
	@RequestMapping("redirect_application_dubbo_project_interface_list")
	public String pageApplicationDubboInterfaceList(ApplicationDto dto , ModelMap model ,HttpSession session){
		model.put("application" , dto.getApplication());
		model.put("nodeAddress" , dto.getNodeAddress());  
		return "jsp/dubbo/admin/dubbo-project-interface-list";
	}
	
	/**
	 * @description:  Dubbo项目指定部署节点下的RPC接口类列表集合|jsp页面数据集合
	 *			http://oms.ostar.com:8080/ostar-oms/application/ajaxFindDubboProjectInterfaceList?application=MATRIX_BIZ_SERVICE&nodeAddress=10.12.51.141:20880
	 *
	 * @param dto.application 	服务名称					必填
	 * @param dto.nodeAddress 	host ip address		必填
	 * 
	 * @author Yangcl
	 * @date 2018年8月30日 下午3:28:14 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajaxFindDubboProjectInterfaceList", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxFindDubboProjectInterfaceList(ApplicationDto dto) {
		return adminApplicationService.ajaxFindDubboProjectInterfaceList(dto);
	}
	
	
	
	
	
	
	
	
	
	/**
	 * @description: 发布服务列表页面
	 *
	 * @param dto
	 * 
	 * @author Yangcl
	 * @date 2018年9月3日 上午10:34:29 
	 * @version 1.0.0.1
	 */
	@RequestMapping("redirect_application_dubbo_project_service_list")
	public String pageApplicationDubboServiceList(AppProvideDto dto , ModelMap model ,HttpSession session){
		model.put("application" , dto.getApplication());
		return "jsp/dubbo/admin/dubbo-project-service-list";
	}
	
	/**
	 * @description: 发布服务列表数据
	 *			http://oms.ostar.com:8080/ostar-oms/application/ajaxFindDubboProjectServiceList?application=MATRIX_AEM_SERVICE
	 *
	 * @param dto.application 	服务名称
	 * 
	 * @author Yangcl
	 * @date 2018年9月3日 上午10:38:39 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajaxFindDubboProjectServiceList", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxFindDubboProjectServiceList(AppProvideDto dto) {
		return adminApplicationService.ajaxFindDubboProjectServiceList(dto);
	}
	
	
	
	/**
	 * @description: 每一个Dubbo服务(生产者)所提供的RPC接口方法所对应的消费者列表|页面地址跳转
	 *
	 * @param dto.id 具体到Dubbo服务所提供的每一个RPC接口，会对应一个长整型ID.
	 * 
	 * @author Yangcl
	 * @date 2018年9月3日 下午3:19:15 
	 * @version 1.0.0.1
	 */
	@RequestMapping("redirect_application_dubbo_project_service_consumer_list")
	public String pageApplicationDubboServiceConsumerList(AppProvideDto dto , ModelMap model ,HttpSession session){
		model.put("id" , dto.getId());
		return "jsp/dubbo/admin/dubbo-project-service-consumer-list";
	}
	
	/**
	 * @description: 每一个Dubbo服务(生产者)所提供的RPC接口方法所对应的消费者列表
	 *			http://oms.ostar.com:8080/ostar-oms/application/ajaxFindDubboProjectServiceConsumerList?id=4
	 *
	 * @param dto.id 具体到Dubbo服务所提供的每一个RPC接口，会对应一个长整型ID.
	 *
	 * @author Yangcl
	 * @date 2018年9月3日 下午3:23:45 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajaxFindDubboProjectServiceConsumerList", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxFindDubboProjectServiceConsumerList(AppProvideDto dto) {
		return adminApplicationService.ajaxFindDubboProjectServiceConsumerList(dto);
	}
	
	
	/**
	 * @description: Dubbo rpc接口方法列表视图
	 *
	 * @param dto.rpcName	com.scrm.services.aem.rpc.IWxQrcodeInfoRpcService
	 * 
	 * @author Yangcl
	 * @date 2018年9月4日 上午10:09:10 
	 * @version 1.0.0.1
	 */
	@RequestMapping("redirect_application_dubbo_project_rpc_function_list")
	public String pageApplicationDubboProjectRpcFunctionList(RpcFunctionDto dto , ModelMap model ,HttpSession session){
		model.put("rpcName" , dto.getRpcName());
		return "jsp/dubbo/admin/dubbo-project-rpc-function-list";
	}
	
	/**
	 * @description: Dubbo rpc接口方法列表数据
	 *			http://oms.ostar.com:8080/ostar-oms/application/ajaxFindDubboProjectRpcFunctionList?rpcName=com.scrm.services.aem.rpc.IWxQrcodeInfoRpcService
	 *
	 * @param dto.rpcName
	 * 
	 * @author Yangcl
	 * @date 2018年9月4日 上午10:09:10 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajaxFindDubboProjectRpcFunctionList", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxFindDubboProjectRpcFunctionList(RpcFunctionDto dto) {
		return adminApplicationService.ajaxFindDubboProjectRpcFunctionList(dto);
	}
	
	
	/**
	 * @description:Dubbo应用服务部署节点列表 
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2019年1月7日 下午2:39:44 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_dubbo_node_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxDubboNodeList(ApplicationDto dto) {
		return adminApplicationService.ajaxDubboNodeList(dto);
	}
}














































































