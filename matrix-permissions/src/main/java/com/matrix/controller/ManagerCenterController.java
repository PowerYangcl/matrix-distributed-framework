package com.matrix.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.matrix.base.BaseController;
import com.matrix.base.Result;
import com.matrix.pojo.entity.McSysFunction;
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
	 * @author Yangcl
	 * @date 2019年11月12日 下午4:33:53 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_platform_info_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<List<McSysFunction>> ajaxPlatformInfoList(HttpSession session) {
		super.userBehavior(session, logger, "ajax_platform_info_list", "获取平台信息列表");
		return mcUserInfoService.ajaxPlatformInfoList();
	}
	
	@RequestMapping(value = "ajax_validate", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<String> ajaxValidate(@Validated ValidationTest dto, HttpSession session) {
		this.getLogger(null).sysoutInfo("validate 测试", this.getClass());
		return Result.SUCCESS("ApiValidationTest return success", dto.toString());
	}
}






























