package com.matrix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.matrix.base.BaseApiDto;
import com.matrix.base.BaseController;
import com.matrix.base.Result;
import com.matrix.service.IApiService;

/**
 * @description: api入口控制器
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年11月10日 上午10:51:38 
 * @version 1.0.0
 */
@Controller
@RequestMapping("matrix")
public class ApiController  extends BaseController{

	@Autowired
	private IApiService service;
	
	
	/**
	 * @description: 开放接口入口描述 
	 *
	 * @param key 请求秘钥
	 * @param value 请求秘钥加密后的消息体
	 * @param json 请求消息体
	 * @author Yangcl
	 * @date 2017年11月10日 上午11:49:21 
	 * @version 1.0.0
	 */
	@RequestMapping(value = "api", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<?> apiService(HttpServletRequest request , HttpServletResponse response , HttpSession session , @Validated BaseApiDto<?> param){ 
		return service.apiService(request , response , session , param);
	}
}































