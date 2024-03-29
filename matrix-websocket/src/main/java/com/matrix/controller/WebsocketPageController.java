package com.matrix.controller;

import javax.servlet.http.HttpSession;

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
		super.userBehavior(session, "page_websocket_affiche_admin", "前往公告发布页面");
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
		super.userBehavior(session, "page_websocket_affiche_user", "前往公告接受页面");
		return "views/websocket/affiche/user";
	}
	
	/**
	 * @description: 前往一对一聊天页面
	 * 
	 * @author Yangcl
	 * @date 2022-3-1 15:14:23
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.0.6-websocket
	 */
	@RequestMapping("page_websocket_p2p")  
	public String websocketP2p(HttpSession session){ 
		super.userBehavior(session, "page_websocket_p2p", "前往一对一聊天页面");
		return "views/websocket/p2p/chat";
	}
	
	/**
	 * @description: 前往多人聊天页面
	 * 
	 * @author Yangcl
	 * @date 2022-3-2 11:42:22
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.0.6-websocket
	 */
	@RequestMapping("page_websocket_group")  
	public String websocketGroup(HttpSession session){ 
		super.userBehavior(session, "page_websocket_p2p", "前往多人聊天页面");
		return "views/websocket/group/group-chat";
	}
}




















































