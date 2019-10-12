package com.matrix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseController;
import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.service.IMcUserInfoService;

/**
 * @description: 系统用户相关控制器
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2019年10月10日 下午8:12:52 
 * @version 1.0.0.1
 */

@Controller
@RequestMapping("userInfo")
public class UserInfoControllor  extends BaseController{
	private static Logger logger = Logger.getLogger(UserInfoControllor.class);
	
	@Autowired
	private IMcUserInfoService mcUserInfoService;
	
	
	/**
	 * @description: 验证用户登录信息|PC端用户
	 * 
	 * @author Yangcl 
	 * @date 2016年11月25日 下午4:17:47 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "login", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject login(HttpServletRequest request , McUserInfoDto info, HttpSession session) {
		logger.info( info.getUserName() + " - 尝试请求 - " + "login() - 方法 - " +  "正在尝试登录"); 
		return mcUserInfoService.login(info, session);
	}
}


















































