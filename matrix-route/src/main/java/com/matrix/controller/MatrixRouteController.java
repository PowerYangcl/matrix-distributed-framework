package com.matrix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseController;
import com.matrix.pojo.dto.PowerCacheDto;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IMatrixRouteService;

/**
 * @description: 消息路由核心提供者
 *
 * @author Yangcl
 * @date 2018年11月20日 下午2:39:02 
 * @version 1.0.0.1
 */
@Controller
@RequestMapping("route")
public class MatrixRouteController extends BaseController{
	
	@Autowired
	private IMatrixRouteService matrixRouteService;
	
	/**
	 * @description: 找到一个指定的一级缓存
	 * 
	 * @param dto.dubboAddr：10.12.52.29:20883
	 * @param dto.cacheName
	 * @param dto.key
	 * 
	 * @return {"msg":"操作成功","code":200,"data":"172.22.134.33","class":"com.matrix.base.RpcResult","status":"success"}
	 * @author Yangcl
	 * @date 2019年1月4日 下午2:40:33 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_route_find", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxRouteFindPowerCache(PowerCacheDto dto, HttpSession session){
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return matrixRouteService.ajaxRouteFindPowerCache(dto);
	} 
	
	/**
	 * @description: 查找多个服务节点中的缓存信息
	 *
 	 * @param dto.dubboAddr：10.12.52.28:20882,10.12.52.29:20883
	 * @param dto.cacheName
	 * @param dto.key
	 * 
	 * @author Yangcl
	 * @date 2019年1月9日 下午5:53:44 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_route_find_all", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxRouteFindAll(PowerCacheDto dto, HttpSession session){
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return matrixRouteService.ajaxRouteFindAll(dto);
	} 
	
	/**
	 * @description: 添加一个或多个一级缓存到指定的节点 
	 *
	 * @param dto.dubboAddr：10.12.52.28:20882,10.12.52.29:20883
	 * @param dto.cacheName
	 * @param dto.key
	 * @param dto.value
	 * 
	 * @return 
	 * @author Yangcl
	 * @date 2019年1月4日 下午4:59:44 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_route_add", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxRouteAddPowerCache(PowerCacheDto dto, HttpSession session){
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return matrixRouteService.ajaxRouteAddPowerCache(dto);
	}
	
	
	/**
	 * @description: 更新一个或多个一级缓存到指定的节点 
	 *
	 * @param dto.dubboAddr：10.12.52.28:20882,10.12.52.29:20883
	 * @param dto.cacheName
	 * @param dto.key
	 * @param dto.value
	 * 
	 * @author Yangcl
	 * @date 2019年1月4日 下午4:59:44 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_route_update", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxRouteUpdatePowerCache(PowerCacheDto dto, HttpSession session){
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return matrixRouteService.ajaxRouteUpdatePowerCache(dto);
	}
	
	
	/**
	 * @description: 删除一个或多个一级缓存到指定的节点 
	 *
	 * @param dto.dubboAddr：10.12.52.28:20882,10.12.52.29:20883
	 * @param dto.cacheName
	 * @param dto.key
	 * @param dto.value
	 * 
	 * @author Yangcl
	 * @date 2019年1月4日 下午4:59:44 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_route_remove", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxRouteRemovePowerCache(PowerCacheDto dto, HttpSession session){
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return matrixRouteService.ajaxRouteRemovePowerCache(dto);
	}
	
	/**
	 * @description: Dubbo应用服务部署节点列表
	 *
	 * @param session
	 * @author Yangcl
	 * @date 2019年1月7日 上午11:37:03 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_route_dubbo_node_list")
	public String pageRouteDubboNodeList(HttpSession session){
		return "jsp/dubbo/route/dubbo-node-list";
	}
	
	
	
	@RequestMapping(value = "ajax_route_execute", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxRouteExecute(PowerCacheDto dto, HttpSession session){
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return matrixRouteService.ajaxRouteExecute(dto);
	}
	
	/**
	 * @description: 服务节点日志页面
	 *
	 * @param session
	 * @author Yangcl
	 * @date 2019年1月14日 下午3:12:33 
	 * @version 1.0.0.1
	 */
	@RequestMapping("show_route_dubbo_logger")
	public String showRouteDubboLogger(HttpSession session , ModelMap map , HttpServletRequest request){
		map.put("host", request.getParameter("host"));
		map.put("app", request.getParameter("app"));
		return "jsp/dubbo/logger/dubbo-logger-page";
	}
	
	/**
	 * @description: 服务器文件列表
 	 *			需要批量设置：matrix-core.guard_file = com.matrix.security.FileGuard
	 *
	 * @param dto.dubboAddr
	 * @param dto.value
	 * 
	 * @author Yangcl
	 * @date 2019年1月24日 下午2:26:15 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_route_dubbo_logger", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxRouteDubboLogger(PowerCacheDto dto, HttpSession session){
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return matrixRouteService.ajaxRouteDubboLogger(dto);
	}
	
	/**
	 * @description: 服务器文件日志页面
	 *
	 * @param session
	 * @author Yangcl
	 * @date 2019年1月24日 下午2:20:18 
	 * @version 1.0.0.1
	 */
	@RequestMapping("show_route_service_files")
	public String showRouteServiceFiles(HttpSession session , ModelMap map , HttpServletRequest request){
		map.put("host", request.getParameter("host"));
		map.put("app", request.getParameter("app"));
		return "jsp/dubbo/file/dubbo-file-page";
	}
	
	/**
	 * @description: 服务器文件列表
	 *
	 * @param dto
	 * @param session
	 * @author Yangcl
	 * @date 2019年1月24日 下午2:26:15 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_route_service_files", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxRouteServiceFiles(PowerCacheDto dto, HttpSession session){
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return matrixRouteService.ajaxRouteServiceFiles(dto);
	}
}










































































