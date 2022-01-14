package com.matrix.system.client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: TODO websocket.client使用
 * 
 * @author Yangcl
 * @date 2022-1-14 18:27:53
 * @home https://github.com/PowerYangcl
 * @path matrix-websocket / com.matrix.system.client.WebSocketHandler.java
 * @version 1.6.0.6-websocket
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class WebSocketHandler extends TextWebSocketHandler {

    private static int counter = 1;

    protected Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

//	private WebSocketConnectionManager manager;
	
//    private void reconnect() {
//        manager.stop();
//        manager.start();
//    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Connection has been established with websocket server. {}", session);
        session.sendMessage(new TextMessage("Great! I am now connected!"));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("Message received : {} ", message.getPayload());
        if (counter < 2) {
            session.sendMessage(new TextMessage("Message number " + counter));
            counter++;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        if (status.getCode() == 1006 || status.getCode() == 1011 || status.getCode() == 1012) {
//            this.reconnect();
        }
    }
}
