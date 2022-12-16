package com.matrix.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.matrix.base.BaseController;
import com.matrix.base.Result;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.service.IMcSysFunctionService;

@Controller
@RequestMapping("manager")
public class ManagerCenterController extends BaseController{
	
	@Autowired
	private IMcSysFunctionService mcSysFunctionService;

	
	
	/**
	 * @description: 获取平台信息列表
	 * 
	 * @author Yangcl
	 * @date 2019年11月12日 下午4:33:53 
	 * @version 1.0.0.1
	 */
	@ResponseBody
	@RequestMapping(value = "ajax_platform_info_list", produces = { "application/json;charset=utf-8" })
	public Result<List<McSysFunction>> ajaxPlatformInfoList(HttpSession session) {
		super.userBehavior(session, "ajax_platform_info_list", "获取平台信息列表");
		return mcSysFunctionService.ajaxPlatformInfoList(session);
	}
	
}






























