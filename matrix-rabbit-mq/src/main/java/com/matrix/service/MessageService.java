package com.matrix.service;

import com.google.common.base.Preconditions;
import com.matrix.converter.IMessageConverter;
import com.matrix.mq.BaseMessage;
import com.matrix.mq.DbMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 *@description:消息发送服务
 *
 *@author Sjh
 *@date 2019/3/16 11:56
 *@version 1.0.1
 */

public class MessageService implements IMessageService {

    private static final String MESSAGE_CANNOT_BE_NULL = "Message cannot be null";

    private RabbitTemplate rabbitTemplate;

    private IMessageConverter messageConverter;

    private ExecutorService executor;


    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    public MessageService(RabbitTemplate rabbitTemplate, IMessageConverter messageConverter) {
        super();
        this.rabbitTemplate = rabbitTemplate;
        this.messageConverter = messageConverter;
        executor = Executors.newCachedThreadPool();
    }


    @Override
    public void send(final String exchange, final String routingKey, final BaseMessage message) {
        final String messagePlain = messageConverter.serialize(Preconditions.checkNotNull(message, MESSAGE_CANNOT_BE_NULL));
        DbMessage dbMessage = new DbMessage(exchange, routingKey, messagePlain, new Date());

        // TODO: 2019/3/16 db记录 sjh

        // 异步线程
        final long id = dbMessage.getId();
        executor.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    //消息延时处理
                    Thread.sleep(1000);

                    rabbitTemplate.convertAndSend(exchange, routingKey, messagePlain);
                    // TODO: 2019/3/16 db记录 sjh
                } catch (Exception e) {
                    LOGGER.error("消息发送异常", e);
                }
            }
        });
    }

    @Override
    public void reSend(long qid, final String exchange, final String routingKey, final BaseMessage message) {
        final String messagePlain = messageConverter.serialize(Preconditions.checkNotNull(message, MESSAGE_CANNOT_BE_NULL));
        DbMessage dbMessage = new DbMessage(exchange, routingKey, messagePlain, new Date());

        // Sync message to queue
        final long id = qid;
        executor.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    rabbitTemplate.convertAndSend(exchange, routingKey, messagePlain);
                    // TODO: 2019/3/16 db记录  sjh
                } catch (AmqpException e) {
                    LOGGER.error("消息发送异常", e);
                }
            }
        });
    }
}
