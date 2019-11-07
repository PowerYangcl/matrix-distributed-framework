package com.matrix.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.matrix.base.BaseController;

/**
 * @descriptions 所有【示例】相关的后台方法都在这里 
 * 
 * @author Yangcl
 * @date 2016年5月28日-下午4:40:04
 * @version 1.0.0
 */
@Controller
@RequestMapping("example")
public class ExampleController extends BaseController{
	private static Logger logger=Logger.getLogger(ExampleController.class);
	
	
	
	@RequestMapping("page_developer_specification")
	public String pageDeveloperSpecification(HttpSession session){
		super.userBehavior(session, logger, "page_developer_specification", "开发者规约-java界面page-developer-specification.jsp");
		return "views/home/tool-bar";
	}	
	
}































