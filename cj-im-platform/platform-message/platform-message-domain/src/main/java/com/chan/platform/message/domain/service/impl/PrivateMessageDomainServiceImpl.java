package com.chan.platform.message.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chan.platform.common.exception.IMException;
import com.chan.platform.common.model.entity.PrivateMessage;
import com.chan.platform.common.model.enums.HttpCode;
import com.chan.platform.common.model.enums.MessageStatus;
import com.chan.platform.common.utils.BeanUtils;
import com.chan.platform.message.domain.event.IMPrivateMessageTxEvent;
import com.chan.platform.message.domain.repository.PrivateMessageRepository;
import com.chan.platform.message.domain.service.PrivateMessageDomainService;
import org.springframework.stereotype.Service;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/29
 * FileName: PrivateMessageDomainServiceImpl
 * Description:
 */
@Service
public class PrivateMessageDomainServiceImpl extends ServiceImpl<PrivateMessageRepository, PrivateMessage> implements PrivateMessageDomainService {
    @Override
    public boolean saveIMPrivateMessageSaveEvent(IMPrivateMessageTxEvent privateMessageTxEvent) {
        if (privateMessageTxEvent == null || privateMessageTxEvent.getPrivateMessageDTO() == null) {
            throw new IMException(HttpCode.PARAMS_ERROR);
        }
        // 保存消息
        PrivateMessage privateMessage = BeanUtils.copyProperties(privateMessageTxEvent.getPrivateMessageDTO(), PrivateMessage.class);
        if (privateMessage == null) {
            throw new IMException(HttpCode.PROGRAM_ERROR, "转换单聊消息失败");
        }
        //设置消息id
        privateMessage.setId(privateMessageTxEvent.getId());
        //设置消息发送人id
        privateMessage.setSendId(privateMessageTxEvent.getSenderId());
        //设置消息状态
        privateMessage.setStatus(MessageStatus.UNSEND.code());
        //设置发送时间
        privateMessage.setSendTime(privateMessageTxEvent.getSendTime());
        //保存数据
        return this.saveOrUpdate(privateMessage);
    }

    @Override
    public boolean checkExists(Long messageId) {
        return baseMapper.checkExists(messageId) != null;
    }
}
