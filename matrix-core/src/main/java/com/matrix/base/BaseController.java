package com.matrix.base;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.matrix.pojo.view.McUserInfoView;

public class BaseController extends BaseClass{

	/**
	 * @description: 用户行为日志记录
	 * 	super.userBehavior(session, logger, "", "");
	 * @param session
	 * @param logger
	 * @param action ：controller 方法名
	 * @param remark ：备注描述，可以为空
	 * 
	 * @author Yangcl 
	 * @date 2017年5月25日 下午2:48:11 
	 * @version 1.0.0.1
	 */
	public boolean userBehavior(HttpSession session , Logger logger , String action , String remark){
		String name = "未授权用户";
		McUserInfoView e = (McUserInfoView) session.getAttribute("userInfo");
		if(e != null){
			name = "用户:  " + e.getUserName();
		}
		this.getLogger(logger).logInfo(name + " - 尝试请求 - " + action + "() - 方法 - " + remark); 
		return true;
	}
	
}























