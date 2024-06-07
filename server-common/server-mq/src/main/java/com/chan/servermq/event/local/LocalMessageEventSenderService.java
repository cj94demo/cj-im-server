package com.chan.servermq.event.local;

import com.alibaba.cola.event.EventBusI;
import com.chan.serverdomain.model.TopicMessage;
import com.chan.servermq.event.MessageEventSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/7 16:21
 * FileName: LocalMessageEventSenderService
 * Description: 本地消息发送
 */
@Component
@ConditionalOnProperty(name = "message.mq.event.type", havingValue = "cola")
public class LocalMessageEventSenderService implements MessageEventSenderService {
    @Autowired
    private EventBusI eventBus;

    @Override
    public boolean send(TopicMessage message) {
        eventBus.fire(message);
        return true;
    }

}
