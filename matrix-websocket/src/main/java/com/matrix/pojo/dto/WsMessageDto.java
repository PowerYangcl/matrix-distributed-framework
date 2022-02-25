package com.matrix.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * @description: 接受客户端数据实体
 * 
 * @author Yangcl
 * @date 2022-1-17 14:31:03
 * @home https://github.com/PowerYangcl
 * @path matrix-websocket / com.matrix.pojo.dto.InMessage.java
 * @version 1.6.0.6-websocket
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WsMessageDto {

    private String from;

    private String to;

    private String content;
    
    private String channel;

    private Date time;

    public WsMessageDto(String content) {
    	this.content = content;
    }
    
    public WsMessageDto(String content, String channel) {
    	this.content = content;
    	this.channel = channel;
    }
    
    public String getFrom() {
        return from;
    }




}
