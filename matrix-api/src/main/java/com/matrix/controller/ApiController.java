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

import com.matrix.base.BaseApiDto;
import com.matrix.base.BaseController;
import com.matrix.base.Result;
import com.matrix.service.IApiService;

/**
 * @description: api入口控制器
 * 		跨域问题：https://blog.csdn.net/sinat_42338962/article/details/104452022
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
	 * 		如果前端发起json请求则会触发跨域保护机制。@CrossOrigin(origins="*",maxAge=3600)指定某个接口全量跨域
	 * 		针对【Controller层验证】
	 * 			@Validated 需要加在要验证的方法中的对象前面，放在类上无效。
	 * 		针对【Service层验证】
	 * 			service【接口】的方法参数上添加注解 @Valid；
	 * 			service【实现类】上加注解 @Validated service实现的方法参数上加注解 @Valid
	 *
	 * @param json 请求消息体
	 * @author Yangcl
	 * @date 2017年11月10日 上午11:49:21 
	 * @version 1.0.0
	 */
	@ResponseBody
	@CrossOrigin(origins="*",maxAge=3600)
	@RequestMapping(value = "api", produces = { "application/json;charset=utf-8" })
	public Result<?> apiService(@RequestBody @Validated BaseApiDto param, HttpServletRequest request , HttpServletResponse response , HttpSession session){ 
		return service.apiService(param, request , response , session);
	}
	
}































