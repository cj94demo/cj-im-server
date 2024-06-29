package com.chan.platform.message.application.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.alibaba.fastjson2.JSONObject;
import com.chan.platform.common.exception.IMException;
import com.chan.platform.common.model.contants.IMPlatformConstants;
import com.chan.platform.common.model.dto.PrivateMessageDTO;
import com.chan.platform.common.model.enums.HttpCode;
import com.chan.platform.common.session.SessionContext;
import com.chan.platform.common.session.UserSession;
import com.chan.platform.dubbo.platform.friend.FriendDubboService;
import com.chan.platform.message.application.service.PrivateMessageService;
import com.chan.platform.message.domain.event.IMPrivateMessageTxEvent;
import com.chan.platform.message.domain.service.PrivateMessageDomainService;
import com.chan.servercache.id.SnowFlakeFactory;
import com.chan.servermq.MessageSenderService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/29
 * FileName: PrivateMessageServiceImpl
 * Description:
 */
@Service
public class PrivateMessageServiceImpl implements PrivateMessageService {
    private final Logger logger = LoggerFactory.getLogger(PrivateMessageServiceImpl.class);
    @DubboReference(version = IMPlatformConstants.DEFAULT_DUBBO_VERSION, check = false)
    private FriendDubboService friendDubboService;
    @Autowired
    private MessageSenderService messageSenderService;
    @Autowired
    private PrivateMessageDomainService privateMessageDomainService;

    @Override
    public Long sendMessage(PrivateMessageDTO dto) {
        UserSession session = SessionContext.getSession();
        Boolean isFriend = friendDubboService.isFriend(session.getUserId(), dto.getRecvId());
        if (BooleanUtil.isFalse(isFriend)){
            throw new IMException(HttpCode.PROGRAM_ERROR, "对方不是你的好友，无法发送消息");
        }
        Long messageId = SnowFlakeFactory.getSnowFlakeFromCache().nextId();
        //组装事务消息数据
        IMPrivateMessageTxEvent imPrivateMessageTxEvent = new IMPrivateMessageTxEvent(messageId,
                session.getUserId(),
                session.getTerminal(),
                IMPlatformConstants.TOPIC_PRIVATE_TX_MESSAGE,
                new Date(),
                dto);
        TransactionSendResult sendResult = messageSenderService.sendMessageInTransaction(imPrivateMessageTxEvent, null);
        if (sendResult.getSendStatus() != SendStatus.SEND_OK){
            logger.error("PrivateMessageServiceImpl|发送事务消息失败|参数:{}", JSONObject.toJSONString(dto));
        }
        return messageId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveIMPrivateMessageSaveEvent(IMPrivateMessageTxEvent privateMessageSaveEvent) {
        return privateMessageDomainService.saveIMPrivateMessageSaveEvent(privateMessageSaveEvent);
    }

    @Override
    public boolean checkExists(Long messageId) {
        return privateMessageDomainService.checkExists(messageId);
    }
}
