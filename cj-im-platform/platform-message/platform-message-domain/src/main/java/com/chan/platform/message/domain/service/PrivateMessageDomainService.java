package com.chan.platform.message.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chan.platform.common.model.entity.PrivateMessage;
import com.chan.platform.message.domain.event.IMPrivateMessageTxEvent;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/29
 * FileName: PrivateMessageDomainService
 * Description:
 */
public interface PrivateMessageDomainService extends IService<PrivateMessage> {
    /**
     * 保存单聊消息
     */
    boolean saveIMPrivateMessageSaveEvent(IMPrivateMessageTxEvent privateMessageSaveEvent);

    /**
     * 检测某条消息是否存在
     */
    boolean checkExists(Long messageId);
}
