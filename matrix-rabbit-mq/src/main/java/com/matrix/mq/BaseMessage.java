package com.matrix.mq;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.UUID;

/**
 *@description:mq 消息 父类
 *    这个类这为每条消息定义 了一个uuid，便于监控、日志、补偿
 *@author Sjh
 *@date 2019/3/16 11:21
 *@version 1.0.1
 */

public abstract class BaseMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    //消息唯一id
    private String id;

    protected BaseMessage() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
