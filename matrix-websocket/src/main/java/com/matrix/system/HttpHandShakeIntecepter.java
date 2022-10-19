package com.matrix.system;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.matrix.base.BaseClass;
import com.matrix.pojo.view.McUserInfoView;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: WebSocket握手请求的拦截器。 检查握手请求和响应，对WebSocketHandler传递属性。
 * 		可以通过这个类的方法获取resuest,和response。
 * 		WebSocketConfig配置endpoint时使用
 * 
 * @author Yangcl
 * @date 2021-7-30 18:22:36
 * @home https://github.com/PowerYangcl
 * @path matrix-websocket / com.matrix.system.HttpHandShakeIntecepter.java
 * @version 1.6.0.6-websocket
 */
@Slf4j
public class HttpHandShakeIntecepter extends BaseClass implements HandshakeInterceptor {

	/**
	 * @description: 在握手之前执行该方法，继续握手返回true，中断握手返回false。
	 * 		 通过attributes参数设置WebSocketSession的属性。
	 * 		这个项目只在WebSocketSession这里存入sessionID
	 *
	 * @author Yangcl
	 * @date 2021-7-31 0:06:03
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.0.6-websocket
	 */
	@Override
	public boolean beforeHandshake(ServerHttpRequest request,  ServerHttpResponse response,  WebSocketHandler wsHandler,  Map<String, Object> attributes) throws Exception {
		if(request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest)request;
			HttpSession session =  servletRequest.getServletRequest().getSession();
			McUserInfoView e = (McUserInfoView) session.getAttribute("userInfo");
			String sessionId = session.getId();
			attributes.put("sessionId", sessionId);	// 这里必须指定为：sessionId，否则请求不到@MessageMapping注解的方法。
			attributes.put(sessionId, e);		// 携带当前登录用户信息
			
			return true;
		}
		return false;
	}

	/**
	 * @description: 在握手之后执行该方法；无论是否握手成功都指明了响应状态码和响应头。
	 *
	 * @author Yangcl
	 * @date 2021-7-31 0:05:21
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.0.6-websocket
	 */
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
		if(request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest)request;
			HttpSession session =  servletRequest.getServletRequest().getSession();
			String sessionId = session.getId();
			log.info("HttpHandShakeIntecepter afterHandshake 握手之后 Session = " + sessionId);
			// TODO 指明状态码？
		}
	}

}














