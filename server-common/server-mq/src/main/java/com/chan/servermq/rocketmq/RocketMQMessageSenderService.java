package com.chan.servermq.rocketmq;

import com.alibaba.fastjson.JSONObject;
import com.chan.serverdomain.constant.IMConstants;
import com.chan.serverdomain.model.TopicMessage;
import com.chan.servermq.MessageSenderService;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/7 16:17
 * FileName: RocketMQMessageSenderService
 * Description:
 */
@Component
@ConditionalOnProperty(name = "message.mq.type", havingValue = "rocketmq")
public class RocketMQMessageSenderService implements MessageSenderService {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public boolean send(TopicMessage message) {
        try {
            SendResult sendResult = rocketMQTemplate.syncSend(message.getDestination(), this.getMessage(message));
            return SendStatus.SEND_OK.equals(sendResult.getSendStatus());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public TransactionSendResult sendMessageInTransaction(TopicMessage message, Object arg) {
        return rocketMQTemplate.sendMessageInTransaction(message.getDestination(), this.getMessage(message), arg);
    }

    //构建ROcketMQ发送的消息
    private Message<String> getMessage(TopicMessage message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(IMConstants.MSG_KEY, message);
        return MessageBuilder.withPayload(jsonObject.toJSONString()).build();
    }

}
