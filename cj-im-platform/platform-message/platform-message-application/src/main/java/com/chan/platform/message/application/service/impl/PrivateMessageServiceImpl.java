package com.chan.platform.message.application.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.BooleanUtil;
import com.alibaba.fastjson2.JSONObject;
import com.chan.platform.common.exception.IMException;
import com.chan.platform.common.model.contants.IMPlatformConstants;
import com.chan.platform.common.model.dto.PrivateMessageDTO;
import com.chan.platform.common.model.enums.HttpCode;
import com.chan.platform.common.model.enums.MessageStatus;
import com.chan.platform.common.model.enums.MessageType;
import com.chan.platform.common.model.vo.PrivateMessageVO;
import com.chan.platform.common.session.SessionContext;
import com.chan.platform.common.session.UserSession;
import com.chan.platform.common.threadpool.PrivateMessageThreadPoolUtils;
import com.chan.platform.common.utils.DateTimeUtils;
import com.chan.platform.dubbo.platform.friend.FriendDubboService;
import com.chan.platform.message.application.service.PrivateMessageService;
import com.chan.platform.message.domain.event.IMPrivateMessageTxEvent;
import com.chan.platform.message.domain.service.PrivateMessageDomainService;
import com.chan.sdk.core.client.IMClient;
import com.chan.servercache.id.SnowFlakeFactory;
import com.chan.servercache.threadpool.ThreadPoolUtils;
import com.chan.servercache.time.SystemClock;
import com.chan.serverdomain.constant.IMConstants;
import com.chan.serverdomain.model.IMPrivateMessage;
import com.chan.serverdomain.model.IMUserInfo;
import com.chan.servermq.MessageSenderService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private IMClient imClient;

    @Override
    public Long sendMessage(PrivateMessageDTO dto) {
        UserSession session = SessionContext.getSession();
        Boolean isFriend = friendDubboService.isFriend(session.getUserId(), dto.getRecvId());
        if (BooleanUtil.isFalse(isFriend)) {
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
        if (sendResult.getSendStatus() != SendStatus.SEND_OK) {
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

    @Override
    public void pullUnreadMessage() {
        UserSession userSession = SessionContext.getSession();
        if (!imClient.isOnline(userSession.getUserId())) {
            throw new IMException(HttpCode.PROGRAM_ERROR, "用户未建立连接");
        }
        List<Long> friendIdList = friendDubboService.getFriendIdList(userSession.getUserId());
        if (CollUtil.isEmpty(friendIdList)) {
            return;
        }
        List<PrivateMessageVO> privateMessageList = privateMessageDomainService.getPrivateMessageVOList(userSession.getUserId(), friendIdList);
        int messageSize = 0;
        if (CollectionUtil.isNotEmpty(privateMessageList)) {
            messageSize = privateMessageList.size();
            privateMessageList.parallelStream().forEach((privateMessageVO) -> {
                // 推送消息
                IMPrivateMessage<PrivateMessageVO> sendMessage = new IMPrivateMessage<>();
                sendMessage.setSender(new IMUserInfo(userSession.getUserId(), userSession.getTerminal()));
                sendMessage.setReceiveId(userSession.getUserId());
                sendMessage.setReceiveTerminals(Collections.singletonList(userSession.getTerminal()));
                sendMessage.setSendToSelf(false);
                sendMessage.setData(privateMessageVO);
                imClient.sendPrivateMessage(sendMessage);
            });
        }
        logger.info("拉取未读私聊消息，用户id:{},数量:{}", userSession.getUserId(), messageSize);
    }

    @Override
    public List<PrivateMessageVO> loadMessage(Long minId) {
        UserSession session = SessionContext.getSession();
        List<Long> friendIdList = friendDubboService.getFriendIdList(session.getUserId());
        if (CollectionUtil.isEmpty(friendIdList)) {
            return Collections.emptyList();
        }
        Date minDate = DateTimeUtils.addMonths(new Date(), -1);
        List<PrivateMessageVO> privateMessageList = privateMessageDomainService.loadMessage(session.getUserId(), minId, minDate, friendIdList, IMPlatformConstants.PULL_HISTORY_MESSAGE_LIMIT_COUNR);
        if (CollectionUtil.isEmpty(privateMessageList)) {
            return Collections.emptyList();
        }
        ThreadPoolUtils.execute(() -> {
            // 更新发送状态
            List<Long> ids = privateMessageList.stream()
                    .filter(m -> !m.getSendId().equals(session.getUserId()) && m.getStatus().equals(MessageStatus.UNSEND.code()))
                    .map(PrivateMessageVO::getId)
                    .collect(Collectors.toList());
            if (!CollectionUtil.isEmpty(ids)) {
                privateMessageDomainService.batchUpdatePrivateMessageStatus(MessageStatus.SENDED.code(), ids);
            }
        });
        logger.info("拉取消息，用户id:{},数量:{}", session.getUserId(), privateMessageList.size());
        return privateMessageList;
    }

    @Override
    public List<PrivateMessageVO> getHistoryMessage(Long friendId, Long page, Long size) {
        page = page > 0 ? page : 1;
        size = size > 0 ? size : 10;
        Long userId = SessionContext.getSession().getUserId();
        long stIdx = (page - 1) * size;
        List<PrivateMessageVO> privateMessageList = privateMessageDomainService.loadMessageByUserIdAndFriendId(userId, friendId, stIdx, size);
        if (CollectionUtil.isEmpty(privateMessageList)) {
            privateMessageList = Collections.emptyList();
        }
        logger.info("拉取聊天记录，用户id:{},好友id:{}，数量:{}", userId, friendId, privateMessageList.size());
        return privateMessageList;
    }

    @Override
    public void readedMessage(Long friendId) {
        if (friendId == null) {
            throw new IMException(HttpCode.PARAMS_ERROR);
        }
        UserSession session = SessionContext.getSession();
        // 推送消息
        PrivateMessageVO msgInfo = new PrivateMessageVO();
        msgInfo.setType(MessageType.READED.code());
        msgInfo.setSendTime(new Date());
        msgInfo.setSendId(session.getUserId());
        msgInfo.setRecvId(friendId);
        IMPrivateMessage<PrivateMessageVO> sendMessage = new IMPrivateMessage<>();
        sendMessage.setSender(new IMUserInfo(session.getUserId(), session.getTerminal()));
        sendMessage.setReceiveId(friendId);
        sendMessage.setSendToSelf(true);
        sendMessage.setData(msgInfo);
        sendMessage.setSendResult(false);
        imClient.sendPrivateMessage(sendMessage);
        PrivateMessageThreadPoolUtils.execute(() -> {
            privateMessageDomainService.updateMessageStatus(MessageStatus.READED.code(), friendId, session.getUserId());
        });
        logger.info("消息已读，接收方id:{},发送方id:{}", session.getUserId(), friendId);
    }

    @Override
    public void recallMessage(Long id) {
        UserSession session = SessionContext.getSession();
        if (id == null) {
            throw new IMException(HttpCode.PARAMS_ERROR);
        }
        PrivateMessageVO privateMessage = privateMessageDomainService.getPrivateMessageById(id);
        if (privateMessage == null) {
            throw new IMException(HttpCode.PROGRAM_ERROR, "消息不存在");
        }
        if (!privateMessage.getSendId().equals(session.getUserId())) {
            throw new IMException(HttpCode.PROGRAM_ERROR, "这条消息不是由您发送,无法撤回");
        }
        if (SystemClock.millisClock().now() - privateMessage.getSendTime().getTime() > IMConstants.ALLOW_RECALL_SECOND * 1000) {
            throw new IMException(HttpCode.PROGRAM_ERROR, "消息已发送超过5分钟，无法撤回");
        }
        //更新消息状态为已撤回
        privateMessageDomainService.updateMessageStatusById(MessageStatus.RECALL.code(), id);
        PrivateMessageThreadPoolUtils.execute(() -> {
            // 推送消息
            privateMessage.setType(MessageType.RECALL.code());
            privateMessage.setSendTime(new Date());
            privateMessage.setContent("对方撤回了一条消息");

            IMPrivateMessage<PrivateMessageVO> sendMessage = new IMPrivateMessage<>();
            sendMessage.setSender(new IMUserInfo(session.getUserId(), session.getTerminal()));
            sendMessage.setReceiveId(privateMessage.getRecvId());
            sendMessage.setSendToSelf(false);
            sendMessage.setData(privateMessage);
            sendMessage.setSendResult(false);
            imClient.sendPrivateMessage(sendMessage);

            // 推给自己其他终端
            privateMessage.setContent("你撤回了一条消息");
            sendMessage.setSendToSelf(true);
            sendMessage.setReceiveTerminals(Collections.emptyList());
            imClient.sendPrivateMessage(sendMessage);
            logger.info("撤回私聊消息，发送id:{},接收id:{}，内容:{}", privateMessage.getSendId(), privateMessage.getRecvId(), privateMessage.getContent());
        });
    }
}
