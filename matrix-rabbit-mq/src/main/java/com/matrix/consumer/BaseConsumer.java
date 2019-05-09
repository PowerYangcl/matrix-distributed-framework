package com.matrix.consumer;


import com.matrix.base.BaseClass;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

/**
 *@description:消费消息
 *
 *@author Sjh
 *@date 2019/3/16 11:26
 *@version 1.0.1
 */

public abstract class BaseConsumer extends BaseClass {

    private Logger logger = Logger.getLogger(BaseConsumer.class);

    public void handle(String message) throws Exception {

        if (StringUtils.isEmpty(message)) {
            this.getLogger(logger).logInfo(" ------->BaseConsumer--null------");
            return;
        }
        this.getLogger(logger).logInfo("Receive message: " + message);
        try {
            doHandle(message);
        } catch (Exception e) {
            this.getLogger(logger).logError("Message: " + message + " with Error: " + e.getMessage());
            throw new Exception(e);
        }
    }

    protected abstract void doHandle(String message);

}