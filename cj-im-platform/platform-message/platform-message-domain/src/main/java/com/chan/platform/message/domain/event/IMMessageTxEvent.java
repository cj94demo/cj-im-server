package com.chan.platform.message.domain.event;

import com.chan.serverdomain.event.IMBaseEvent;

import java.util.Date;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/7/2 19:37
 * FileName: IMMessageTxEvent
 * Description: IMMessageTxEvent
 */
public class IMMessageTxEvent extends IMBaseEvent {
    //消息发送人id
    private Long senderId;
    //终端类型
    private Integer terminal;
    //发送时间
    private Date sendTime;
    //type_private: 单聊消息; type_group:群聊消息
    private String messageType;

    public IMMessageTxEvent() {
    }

    public IMMessageTxEvent(Long id, Long senderId, Integer terminal, Date sendTime, String destination, String messageType) {
        super(id, destination);
        this.senderId = senderId;
        this.terminal = terminal;
        this.sendTime = sendTime;
        this.messageType = messageType;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Integer getTerminal() {
        return terminal;
    }

    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
