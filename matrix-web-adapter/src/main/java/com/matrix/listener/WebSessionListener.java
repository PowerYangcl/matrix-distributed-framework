package com.matrix.listener;

//import javax.servlet.annotation.WebListener;
//import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


//import com.matrix.pojo.view.McUserInfoView;

/**
 * @description: 监听web session
 * 
 * @author Yangcl
 * @date 2022-3-2 18:05:25
 * @home https://github.com/PowerYangcl
 * @path matrix-websocket / com.matrix.listener.WebsocketSessionListener.java
 * @version 1.6.0.6-websocket
 */
//@WebListener
public class WebSessionListener implements HttpSessionListener, HttpSessionAttributeListener{

	@Override
    public void sessionCreated(HttpSessionEvent event) {
		
    }


	/**
	 * @description: 退出登录后触发
	 *
	 * @author Yangcl
	 * @date 2022-3-2 18:47:13
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.0.6-websocket
	 */
	@Override
    public void sessionDestroyed(HttpSessionEvent event) {
//		HttpSession session = event.getSession();
//		McUserInfoView e = (McUserInfoView) session.getAttribute("userInfo");
		
    }
	
	/**
	 * @description: login接口写session时会触发此监听
	 *
	 * @author Yangcl
	 * @date 2022-3-2 18:44:43
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.0.6-websocket
	 */
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		
    }
}



























