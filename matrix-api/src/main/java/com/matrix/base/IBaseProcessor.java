package com.matrix.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @description: 开放接口顶层抽象|所有开放给第三方的接口以及公司内部的
 * 		api接口全部需要实现这个类的方法
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年11月13日 上午11:32:29 
 * @version 1.0.0.1
 * @param <T>
 */
public interface IBaseProcessor {
	
	/**
	 * @description: 每个业务对应的实现类均在此处进行详细逻辑处理 
	 *
	 * @param request
	 * @param response
	 * @param param 完整的JSONObject消息请求对象，包含所有信息
	 * @return 
	 * @author Yangcl
	 * @date 2017年11月13日 上午11:54:05 
	 * @version 1.0.0
	 */
	public Result<?> processor(BaseApiDto param, HttpServletRequest request, HttpServletResponse response , HttpSession session); 
	
}
