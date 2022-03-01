package com.matrix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.matrix.pojo.dto.WsMessageDto;
import com.matrix.pojo.view.WsMessageView;

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
public class WebsocketController{
	
    @Autowired
    private SimpMessagingTemplate template;

    /**
     * @description: 网站公告信息
     * 		MessageMapping 和 RequestMapping功能类似，用于设置URL映射地址，
     * 		浏览器向服务器发起请求，需要通过该地址。
     * 		如果服务器接受到了消息，就会对订阅了括号中的地址传送消息。
     * 		@SendTo 注解不灵活，不建议使用。
     * 
     * @author Yangcl
     * @date 2022-1-17 14:48:11
     * @home https://github.com/PowerYangcl
     * @version 1.6.0.6-websocket
     */
    @MessageMapping("/affiche/pagechat")
    public void afficheInfo(WsMessageDto msg) {
		System.out.println("SimpMessagingTemplate msg = " + msg.getContent() + " channel = " + msg.getChannel());
		template.convertAndSend("/subscribe-page/affiche",new WsMessageView(msg.getContent(), msg.getChannel()));
    }
    
    /**
     * @description: 一对一聊天，页面：订阅自己发送给别人
     * 
     * @author Yangcl
     * @date 2022-3-1 20:38:43
     * @home https://github.com/PowerYangcl
     * @version 1.6.0.6-websocket
     */
    @MessageMapping("/p2p/chat")
    public void p2pChat(WsMessageDto msg) {
		System.out.println("SimpMessagingTemplate p2p msg = " + msg.getContent() + " from = " + msg.getFrom() + " to = " + msg.getTo());
		template.convertAndSend("/subscribe-page/chat/" + msg.getTo(), new WsMessageView(msg.getTo(), msg.getFrom(), msg.getContent()));
    }
}




















































