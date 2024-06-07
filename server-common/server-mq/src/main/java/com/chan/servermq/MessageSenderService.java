package com.chan.servermq;

import com.chan.serverdomain.model.TopicMessage;
import org.apache.rocketmq.client.producer.TransactionSendResult;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/7 16:16
 * FileName: MessageSenderService
 * Description: 消息发送服务
 */
public interface MessageSenderService {
    /**
     * 发送消息
     *
     * @param message 发送的消息
     */
    boolean send(TopicMessage message);

    /**
     * 发送事务消息，主要是RocketMQ
     *
     * @param message 事务消息
     * @param arg     其他参数
     * @return 返回事务发送结果
     */
    default TransactionSendResult sendMessageInTransaction(TopicMessage message, Object arg) {
        return null;
    }

}
