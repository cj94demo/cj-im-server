package com.chan.platform.message.application.service;

import com.chan.platform.common.model.dto.PrivateMessageDTO;
import com.chan.platform.message.domain.event.IMPrivateMessageTxEvent;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/29 14:19
 * FileName: PrivateMessageService
 * Description: 单聊消息
 */
public interface PrivateMessageService {
    /**
     * 发送私聊消息
     *
     * @param dto 私聊消息
     * @return 消息id
     */
    Long sendMessage(PrivateMessageDTO dto);

    /**
     * 保存单聊消息
     */
    boolean saveIMPrivateMessageSaveEvent(IMPrivateMessageTxEvent privateMessageSaveEvent);

    /**
     * 检测数据
     */
    boolean checkExists(Long messageId);
}
