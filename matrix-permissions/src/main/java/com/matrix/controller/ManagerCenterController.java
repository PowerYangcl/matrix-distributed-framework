package com.matrix.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseController;
import com.matrix.service.IMcUserInfoService;

@Controller
@RequestMapping("manager")
public class ManagerCenterController extends BaseController{
	private static Logger logger = Logger.getLogger(ManagerCenterController.class);
	
	@Autowired
	private IMcUserInfoService mcUserInfoService;

	
	
	
	/**
	 * @description: 获取平台信息列表
	 * 
	 * 		修改前名称：ajax_draw_add_user_page
	 *
	 * @author Yangcl
	 * @date 2019年11月12日 下午4:33:53 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_platform_info_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxPlatformInfoList(HttpSession session) {
		super.userBehavior(session, logger, "ajax_platform_info_list", "获取平台信息列表");
		return mcUserInfoService.ajaxPlatformInfoList();
	}
	
	
}






























