package com.matrix.converter;


import com.matrix.mq.BaseMessage;

/**
 *@description: 消息转换接口，用于序列化消息体
 *
 *
 *@author Sjh
 *@date 2019/3/16 11:24
 *@version 1.0.1
 */

public interface IMessageConverter {

    <T extends BaseMessage> String serialize(T message);

    <T extends BaseMessage> T deserialize(String msg, Class<T> messageClazz);
}
