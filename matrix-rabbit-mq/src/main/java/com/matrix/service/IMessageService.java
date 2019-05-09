package com.matrix.service;


import com.matrix.mq.BaseMessage;


/**
 *@description: mq 发消息接口
 *
 *@author Sjh
 *@date 2019/3/16 11:19
 *@version 1.0.1
 */

public interface IMessageService {

    /**
     * 发送消息
     * 先落盘，再发送
     *
     * @param exchange   交换机
     * @param routingKey 选择键
     * @param message    消息
     */
    void send(final String exchange, String routingKey, BaseMessage message);


    /**
     * 重新发送消息
     * 先发送，无异常不落盘
     *
     * @param id         主键ID
     * @param exchange   交换机
     * @param routingKey 选择键
     * @param message    消息
     */
    void reSend(long id, final String exchange, String routingKey, BaseMessage message);
}
