package com.matrix.system;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

/**
 * @description: 频道拦截器 ，类似管道，可以获取消息的一些meta数据
 * 
 * @author Yangcl
 * @date 2021-7-30 18:22:12
 * @home https://github.com/PowerYangcl
 * @path matrix-websocket / com.matrix.system.SocketChannelIntecepter.java
 * @version 1.6.0.6-websocket
 */
public class SocketChannelIntecepter implements ChannelInterceptor{
	
	/**
	 * @description: 在完成发送之后进行调用，不管是否有异常发生，一般用于资源清理
	 *
	 * @author Yangcl
	 * @date 2022-1-11 19:48:33
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.0.6-websocket
	 */
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        System.out.println("SocketChannelIntecepter->afterSendCompletion");
    }
    
    /**
     * @description: 在消息被实际发送到频道之前调用
     *
     * @author Yangcl
     * @date 2022-1-11 19:49:53
     * @home https://github.com/PowerYangcl
     * @version 1.6.0.6-websocket
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("SocketChannelIntecepter->preSend");
        return preSend(message, channel);
    }
    
    /**
     * @description: 发送消息调用后立即调用
     *
     * @author Yangcl
     * @date 2022-1-11 19:50:01
     * @home https://github.com/PowerYangcl
     * @version 1.6.0.6-websocket
     */
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        System.out.println("SocketChannelIntecepter->postSend");
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);//消息头访问器
        if (headerAccessor.getCommand() == null) {
        	return;// 避免非stomp消息类型，例如心跳检测
        }
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        System.out.println("SocketChannelIntecepter -> sessionId = " + sessionId);
        switch (headerAccessor.getCommand()) {
            case CONNECT:
                connect(sessionId);
                break;
            case DISCONNECT:
                disconnect(sessionId);
                break;
            case SUBSCRIBE:
                break;
            case UNSUBSCRIBE:
                break;
            default:
                break;
        }
    }
    
    /**
     * @description: 连接成功
     * 
     * @param sessionId void
     * @author Yangcl
     * @date 2022-1-11 19:50:13
     * @home https://github.com/PowerYangcl
     * @version 1.6.0.6-websocket
     */
    private void connect(String sessionId) {
        System.out.println("connect sessionId=" + sessionId);
    }

    /**
     * @description: 断开连接  TODO 如果断开连接，尝试重新连接，5次。
     * 
     * @author Yangcl
     * @date 2022-1-11 19:50:20
     * @home https://github.com/PowerYangcl
     * @version 1.6.0.6-websocket
     */
    private void disconnect(String sessionId) {
        System.out.println("disconnect sessionId=" + sessionId);
        //用户下线操作
//        UserChatController.onlineUser.remove(sessionId);
    }
}















