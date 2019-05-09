package com.matrix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseController;
import com.matrix.pojo.entity.TcLandedProperty;
import com.matrix.service.ITcLandedPropertyService;

/**
 * @description: 这个类尽可能的贴近实际业务来展示一个完整的开发流程。
 * 
 * 	这个类包含了编写业务所需的全部可能。当你在系统中添加一个新的业务，
 * 	无论是什么样的业务内容，均可在此处复制得出。这是一个样板类，体现了“复制即所得”的思想。
 * 	但请注意修改对应的类名称、Logger里的类名、RequestMapping等等。
 * 	一个完整的业务流程，依赖的类大体结构如下：
 * 		DemoController.java
 * 			-- ITcLandedPropertyService.java
 * 			-- TcLandedPropertyServiceImpl.java
 * 					-- ITcLandedPropertyDao.java
 * 					-- TcLandedProperty.java (对应数据表的entity，还可能包含如：dto、view等等传输实体)
 * 			-- TcLandedPropertyMapper.xml
 * 
 * 		请注意！所有的业务操作都应该在Service层来完成，不要侵入到Controller中，这将降低程序移植过程中的灵活性。
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年2月20日 下午4:06:29 
 * @version 1.0.0.1
 */
@Controller
@RequestMapping("demos")
public class DemosController extends BaseController{
	private static Logger logger=Logger.getLogger(DemosController.class);
	
	@Autowired
	private ITcLandedPropertyService service;

	/**
	 * @description: 前往楼盘信息列表页
	 *
	 * @param ModelMap 为页面传递EL表达式中的参数，这里没有使用，仅作为参考，提醒使用者可以这样用。
	 * 		具体请查阅 org.springframework.ui.ModelMap
	 * 
	 * @author Yangcl
	 * @date 2018年2月21日 下午3:16:38 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_demo_landed_property")  
	public String pageDemoLandedProperty(HttpSession session , ModelMap model){ 
		super.userBehavior(session, logger, "page_demo_landed_property", "前往楼盘信息列表页");
		return service.pageDemoLandedProperty(model); 
	}
	
	/**
	 * @description: 返回楼盘信息列表页数据
	 *
	 * @param entity 数据查询实体对象。可以是dto也可以是entity
	 * @param request
	 * @param session
	 * @author Yangcl
	 * @date 2018年2月21日 下午3:17:24 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_demo_landed_property_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxDemoLandedPropertyList(TcLandedProperty entity , HttpServletRequest request, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_demo_landed_property_list", "返回楼盘信息列表页数据");
		return service.ajaxDemoLandedPropertyList(entity, request, session);  
	}
}








































