package com.matrix.launch;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.matrix.base.BaseClass;
import com.matrix.controller.WebsocketController;
import com.matrix.system.HttpHandShakeIntecepter;
import com.matrix.system.SocketChannelIntecepter;

/**
 * @description: 
 * 		经典使用场景
 * 								1对1：点对点聊天；
 * 								1对多：游戏公告；
 * 								多对多：多人聊天系统
 * 
 * 		@EnableWebSocketMessageBroker：在 WebSocket 上启用STOMP
 * 
 * 		参考：https://www.jianshu.com/p/9103c9c7e128
 * 					https://blog.csdn.net/pacosonswjtu/article/details/51914567
 * 					https://blog.csdn.net/qq_42809896/article/details/103896537  【两个springboot+websocket之间的通信 WebSocketClient 】
 * 					https://stackoverflow.com/questions/51873661/
 * 
 * @author Yangcl
 * @date 2021-7-30 18:41:26
 * @home https://github.com/PowerYangcl
 * @path matrix-websocket / com.matrix.launch.WebSocketConfig.java
 * @version 1.6.0.6-websocket
 */
@Configuration
@EnableWebSocketMessageBroker		// 在 WebSocket 上启用 STOMP
@ComponentScan(basePackageClasses = {WebsocketController.class}) 	// 后启动尚未为扫描到 https://blog.csdn.net/ilovemvc/article/details/120813727
public class WebSocketConfig extends BaseClass implements WebSocketMessageBrokerConfigurer {
	
	/**
	 * @description: 配置基站。添加Endpoint，在网页中就可以通过websocket连接上服务，
	 * 		也就是我们配置websocket的服务地址，并且可以指定是否使用socketjs
	 * 		页面通过：var socket = new SockJS('/endpoint-websocket');
	 * 		
	 * 		setAllowedOriginPatterns ("*")非必须，"*" 表示允许其他域进行连接。最好取配置文件中的内容
	 * 		withSockJS  表示开始sockejs支持
	 *
	 * @author Yangcl
	 * @date 2021-7-30 18:17:50
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.0.6-websocket
	 */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    	String[] arr = this.getConfig("matrix-websocket.endpoint").split(",");
    	registry.addEndpoint(arr)		// "/matrix-endpoint"
    		.addInterceptors(new HttpHandShakeIntecepter())
    		.setAllowedOriginPatterns("*")
    		.withSockJS();
//        registry.addEndpoint(arr).addInterceptors(new HttpHandShakeIntecepter()).setAllowedOrigins("*").withSockJS();
    }

    /**
     * @description: 配置消息代理，哪种路径的消息会进行代理处理
     *
     * @author Yangcl
     * @date 2021-7-30 18:17:06
     * @home https://github.com/PowerYangcl
     * @version 1.6.0.6-websocket
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(this.getConfig("matrix-websocket.broker").split(","));
        registry.setApplicationDestinationPrefixes(this.getConfig("matrix-websocket.application_destination_prefixes").split(","));
    }


    /**
     * @description: 设置【输入】消息通道的线程数。默认线程为1，可以自己自定义线程数，最大线程数和线程存活时间。
     *
     * @author Yangcl
     * @date 2021-7-30 18:48:02
     * @home https://github.com/PowerYangcl
     * @version 1.6.0.6-websocket
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new SocketChannelIntecepter());
        
        ThreadPoolTaskExecutor task = new ThreadPoolTaskExecutor();
        task.setCorePoolSize(2);
        task.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
        task.setThreadNamePrefix("matrix-websocket-message-chnanel");
        registration.taskExecutor(new ThreadPoolTaskExecutor());
    }

    /**
     * @description: 设置【输出】消息通道的线程数。默认线程为1，可以自己自定义线程数，最大线程数和线程存活时间。
     *
     * @author Yangcl
     * @date 2021-7-30 18:48:39
     * @home https://github.com/PowerYangcl
     * @version 1.6.0.6-websocket
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(new SocketChannelIntecepter());
        
        ThreadPoolTaskExecutor task = new ThreadPoolTaskExecutor();
        task.setCorePoolSize(2);
        task.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
        task.setThreadNamePrefix("matrix-websocket-message-chnanel");
        registration.taskExecutor(new ThreadPoolTaskExecutor());
    }
    
    /**
     * @description: 添加自定义的消息转换器。spring 提供多种默认的消息转换器，
     * 		return false，不会添加消息转换器；
     * 		return true，会添加默认的消息转换器；
     * 		也可以把自己写的消息转换器添加到转换链中。
     *
     * @author Yangcl
     * @date 2021-7-30 18:50:09
     * @home https://github.com/PowerYangcl
     * @version 1.6.0.6-websocket
     */
//    @Override
//    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
////    	messageConverters.add(new GsonMessageConverter());
//		return true;
//	}
    
}













