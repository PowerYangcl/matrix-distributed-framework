package com.matrix.launch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import com.matrix.base.BaseClass;
import com.matrix.system.client.Constants;
import com.matrix.system.client.WebSocketHandler;

/**
 * @description: WebSocketClient配置
 * 			https://stackoverflow.com/questions/51873661/
 * 
 * @author Yangcl
 * @date 2022-1-5 17:43:12
 * @home https://github.com/PowerYangcl
 * @path matrix-websocket / com.matrix.launch.WebSocketClientConfig.java
 * @version 1.6.0.6-websocket
 */
//@Configuration
public class WebSocketClientConfig{	// extends BaseClass {

	// TODO 需要一个bean的顺序加载来解决此类中reconnect()的问题
//	@Autowired
//	private WebSocketHandler handler = new WebSocketHandler();
//	
//	private WebSocketConnectionManager manager;
//	
//    @Bean
//    public WebSocketConnectionManager wsConnectionManager() {
//        manager = new WebSocketConnectionManager(client(), handler, Constants.server_websocket_url);
//        manager.setAutoStartup(true);			// 重要
//        return manager;
//    }
//    
//    @Bean
//    public StandardWebSocketClient client() {
//        return new StandardWebSocketClient();
//    }
}

































