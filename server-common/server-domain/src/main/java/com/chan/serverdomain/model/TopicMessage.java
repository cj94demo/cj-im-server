package com.chan.serverdomain.model;

import com.alibaba.cola.event.DomainEventI;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/6 20:54
 * FileName: TopicMessage
 * Description: RocketMQ消息模型
 */
public class TopicMessage implements DomainEventI {
    private static final long serialVersionUID = 1L;

    private String destination;

    public TopicMessage() {
    }

    public TopicMessage(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
