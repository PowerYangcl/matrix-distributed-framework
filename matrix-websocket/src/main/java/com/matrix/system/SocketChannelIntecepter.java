package com.matrix.system;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;

/**
 * @description: 频道拦截器 ，类似管道，可以获取消息的一些meta数据
 * 
 * @author Yangcl
 * @date 2021-7-30 18:22:12
 * @home https://github.com/PowerYangcl
 * @path matrix-websocket / com.matrix.system.SocketChannelIntecepter.java
 * @version 1.6.0.6-websocket
 */
public class SocketChannelIntecepter extends BaseClass implements ChannelInterceptor{
	
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
        this.getLogger(null).sysoutInfo("afterSendCompletion 完成发送之后进行调用 " + message, this.getClass()); 
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
        this.getLogger(null).sysoutInfo("preSend 消息被实际发送到频道之前调用 " + JSONObject.toJSONString(message), this.getClass());
        return message;
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
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);//消息头访问器
        if (headerAccessor.getCommand() == null) {
        	return;	// 避免非stomp消息类型，例如心跳检测
        }
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        this.getLogger(null).sysoutInfo("postSend 发送消息调用后立即调用 sessionId" + sessionId, this.getClass()); 
        
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
        //用户下线操作
//        UserChatController.onlineUser.remove(sessionId);
    }
}















