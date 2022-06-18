package com.matrix.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.matrix.aspectj.Idempotent;
import com.matrix.aspectj.IdempotentRequest;
import com.matrix.base.BaseController;
import com.matrix.base.Result;
import com.matrix.util.DateUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @description: Jsp Controller层的幂等性测试
 * 
 * @author Yangcl
 * @date 2022-6-18 19:58:30
 * @home https://github.com/PowerYangcl
 * @path leader / com.matrix.controller.IdempotentTestController.java
 * @version 1.6.1.0-Idempotent
 */
@Controller
@RequestMapping("ide")
public class IdempotentTestController extends BaseController{

	/**
	 * @description: 使用 @Idempotent 标签进行接口幂等性拦截的测试接口
	 * 		http://localhost:8080/leader/ide/api_with_dto_request.do?name=admin&type=root&psw=A2022&clientToken=99837948753279
	 * 
	 * @param param  参数：JspRequest extends IdempotentRequest
	 * @param request		必传，否则IdempotentAspect.java取值为空
	 * @param session		必传，否则IdempotentAspect.java取值为空
	 * @author Yangcl
	 * @date 2022-6-18 21:04:50
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.1.0-Idempotent
	 */
	@ResponseBody
	@Idempotent(accessToken = false)		// 默认就是false，可以不写
	@RequestMapping(value = "api_with_dto_request", produces = { "application/json;charset=utf-8" })
	public Result<?> apiWithDtoRequest(JspRequest param, HttpServletRequest request, HttpSession session){ 
		DateUtil du = new DateUtil();
		String time = du.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		this.getLogger(null).sysoutInfo("api_with_dto_request time = " + time, this.getClass());
		return Result.SUCCESS("api_with_dto_request.do time = " + time + " param = " + JSONObject.toJSONString(param));
	}
	
	
	/**
	 * @description: 使用 @Idempotent 标签进行接口幂等性拦截的测试接口，但不传入参数
	 * 		此种情况会触发幂等拦截，但因为没有参数实体，故不会触发拦截
	 * 		http://localhost:8080/leader/ide/api_without_dto_request.do
	 * 
	 * @author Yangcl
	 * @date 2022-6-18 21:04:50
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.1.0-Idempotent
	 */
	@ResponseBody
	@Idempotent(accessToken = false)		// 默认就是false，可以不写
	@RequestMapping(value = "api_without_dto_request", produces = { "application/json;charset=utf-8" })
	public Result<String> apiWithoutDtoRequest(HttpServletRequest request, HttpSession session){ 
		DateUtil du = new DateUtil();
		String time = du.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		this.getLogger(null).sysoutInfo("api_without_dto_request time = " + time, this.getClass());
		return Result.SUCCESS("api_without_dto_request.do time = " + time);
	}
	
	
	/**
	 * @description: 不使用 @Idempotent 标签进行接口幂等性拦截
	 * 		此种情况不会触发拦截器，因为根本没进入拦截器
	 * 		http://localhost:8080/leader/ide/api_without_idempotent_request.do
	 * 
	 * @author Yangcl
	 * @date 2022-6-18 21:04:50
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.1.0-Idempotent
	 */
	@ResponseBody
	@RequestMapping(value = "api_without_idempotent_request", produces = { "application/json;charset=utf-8" })
	public Result<String> apiWithoutIdempotent(HttpServletRequest request, HttpSession session){ 
		DateUtil du = new DateUtil();
		String time = du.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		this.getLogger(null).sysoutInfo("api_without_idempotent_request time = " + time, this.getClass());
		return Result.SUCCESS("api_without_idempotent_request.do time = " + time);
	}
	
	@Data
	@EqualsAndHashCode(callSuper=false)
	private class JspRequest extends IdempotentRequest{
		private String name;
		private String type;
		private String psw;
	}
}































