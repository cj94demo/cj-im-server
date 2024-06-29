package com.chan.platform.message.domain.event;

import com.chan.platform.common.model.dto.PrivateMessageDTO;
import com.chan.serverdomain.event.IMBaseEvent;

import java.util.Date;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/29
 * FileName: IMPrivateMessageTxEvent
 * Description:
 */
public class IMPrivateMessageTxEvent extends IMBaseEvent {
    //消息发送人id
    private Long senderId;
    //终端类型
    private Integer terminal;
    //发送时间
    private Date sendTime;
    //消息数据
    private PrivateMessageDTO privateMessageDTO;

    public IMPrivateMessageTxEvent() {
    }

    public IMPrivateMessageTxEvent(Long id, Long senderId, Integer terminal, String destination, Date sendTime, PrivateMessageDTO privateMessageDTO) {
        super(id, destination);
        this.senderId = senderId;
        this.sendTime = sendTime;
        this.terminal = terminal;
        this.privateMessageDTO = privateMessageDTO;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public PrivateMessageDTO getPrivateMessageDTO() {
        return privateMessageDTO;
    }

    public void setPrivateMessageDTO(PrivateMessageDTO privateMessageDTO) {
        this.privateMessageDTO = privateMessageDTO;
    }

    public Integer getTerminal() {
        return terminal;
    }

    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }
}
