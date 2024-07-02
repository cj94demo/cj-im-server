package com.chan.platform.message.domain.event;

import com.chan.platform.common.model.contants.IMPlatformConstants;
import com.chan.platform.common.model.dto.PrivateMessageDTO;

import java.util.Date;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/29
 * FileName: IMPrivateMessageTxEvent
 * Description:
 */
public class IMPrivateMessageTxEvent extends IMMessageTxEvent {
    //消息数据
    private PrivateMessageDTO privateMessageDTO;

    public IMPrivateMessageTxEvent() {
    }

    public IMPrivateMessageTxEvent(Long id, Long senderId, Integer terminal, String destination, Date sendTime, PrivateMessageDTO privateMessageDTO) {
        super(id, senderId, terminal, sendTime, destination, IMPlatformConstants.TYPE_MESSAGE_PRIVATE);
        this.privateMessageDTO = privateMessageDTO;
    }

    public PrivateMessageDTO getPrivateMessageDTO() {
        return privateMessageDTO;
    }

    public void setPrivateMessageDTO(PrivateMessageDTO privateMessageDTO) {
        this.privateMessageDTO = privateMessageDTO;
    }
}
