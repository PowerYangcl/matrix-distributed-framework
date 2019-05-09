package com.matrix.converter;

import com.google.gson.Gson;
import com.matrix.mq.BaseMessage;


/**
 *@description: 消息序列化/反序列化：by gson
 *
 *@author Sjh
 *@date 2019/3/16 11:25
 *@version 1.0.1
 */

public class MessageConverter implements IMessageConverter {

    private Gson gson;

    public MessageConverter(Gson gson) {
        super();
        this.gson = gson;
    }

    @Override
    public <T extends BaseMessage> String serialize(T message) {
        return gson.toJson(message);
    }

    @Override
    public <T extends BaseMessage> T deserialize(String msg, Class<T> messageClazz) {
        return gson.fromJson(msg, messageClazz);
    }
}
