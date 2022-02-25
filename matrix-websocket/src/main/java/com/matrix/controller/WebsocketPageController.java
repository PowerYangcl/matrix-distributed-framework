package com.matrix.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.matrix.base.BaseController;

/**
 * @description: websocket通信支持类
 * 
 * @author Yangcl
 * @date 2021-7-27 18:16:27
 * @home https://github.com/PowerYangcl
 * @path matrix-websocket / com.matrix.controller.WebsocketController.java
 * @version 1.6.0.6-websocket
 */
@Controller
@RequestMapping("websocket")
public class WebsocketPageController extends BaseController{
	
	private static Logger logger = Logger.getLogger(WebsocketPageController.class);
	
	/**
	 * @description: 前往公告发布页面
	 * 
	 * @author Yangcl
	 * @date 2022-2-21 17:14:22
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.0.6-websocket
	 */
	@RequestMapping("page_websocket_affiche_admin")  
	public String websocketAfficheAdmin(HttpSession session){ 
		super.userBehavior(session, logger, "page_websocket_affiche_admin", "前往公告发布页面");
		return "views/websocket/affiche/admin";
	}
	
	/**
	 * @description: 前往公告接受页面
	 * 
	 * @author Yangcl
	 * @date 2022-2-21 17:21:48
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.0.6-websocket
	 */
	@RequestMapping("page_websocket_affiche_user")  
	public String websocketAfficheUser(HttpSession session){ 
		super.userBehavior(session, logger, "page_websocket_affiche_user", "前往公告接受页面");
		return "views/websocket/affiche/user";
	}
}




















































