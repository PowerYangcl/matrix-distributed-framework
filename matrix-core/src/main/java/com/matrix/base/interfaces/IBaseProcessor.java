package com.matrix.base.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

/**
 * @description: 开放接口顶层抽象|所有开放给第三方的接口以及公司内部的
 * 		api接口全部需要实现这个类的方法
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年11月13日 上午11:32:29 
 * @version 1.0.0
 */
public interface IBaseProcessor {
	
	/**
	 * @description: 每个业务对应的实现类均在此处进行详细逻辑处理 
	 *
	 * @param request
	 * @param response
	 * @param data 完整的JSONObject消息请求对象，包含所有信息
	 * @return 
	 * @author Yangcl
	 * @date 2017年11月13日 上午11:54:05 
	 * @version 1.0.0
	 */
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response , JSONObject data); 
}
