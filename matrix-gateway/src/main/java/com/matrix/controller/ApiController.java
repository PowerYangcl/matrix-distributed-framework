package com.matrix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.matrix.aspectj.Idempotent;
import com.matrix.base.BaseApiDto;
import com.matrix.base.BaseController;
import com.matrix.base.Result;
import com.matrix.service.IApiService;

@Controller
@RequestMapping("matrix")
public class ApiController  extends BaseController{

	@Autowired
	private IApiService service;
	
	@ResponseBody
	@Idempotent(accessToken = true)		// 幂等验证取当且方法的accessToken字段
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value = "api", produces = { "application/json;charset=utf-8" })
	public Result<?> apiService(@RequestBody @Validated BaseApiDto param, HttpServletRequest request , HttpServletResponse response , HttpSession session){ 
		return service.apiService(param, request , response , session);
	}
	
}































