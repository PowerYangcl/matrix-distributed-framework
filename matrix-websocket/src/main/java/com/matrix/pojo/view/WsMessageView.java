package com.matrix.pojo.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description: 服务端向客户端传入实体类
 * 
 * @author Yangcl
 * @date 2022-1-17 14:32:04
 * @home https://github.com/PowerYangcl
 * @path matrix-websocket / com.matrix.pojo.view.OutMessage.java
 * @version 1.6.0.6-websocket
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WsMessageView {

    private String from;

    private String content;
    
    private String channel;

    private Date time = new Date();


    public WsMessageView(String content) {
        this.content = content;
    }


	public WsMessageView(String content, String channel) {
		this.content = content;
		this.channel = channel;
	}
    
    
}

