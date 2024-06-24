package com.chan.platform.friend.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chan.platform.common.exception.IMException;
import com.chan.platform.common.model.contants.IMPlatformConstants;
import com.chan.platform.common.model.entity.Friend;
import com.chan.platform.common.model.enums.HttpCode;
import com.chan.platform.common.model.vo.FriendVO;
import com.chan.platform.friend.domain.event.IMFriendEvent;
import com.chan.platform.friend.domain.model.command.FriendCommand;
import com.chan.platform.friend.domain.repository.FriendRepository;
import com.chan.platform.friend.domain.service.FriendDomainService;
import com.chan.servercache.id.SnowFlakeFactory;
import com.chan.servermq.event.MessageEventSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/24 21:11
 * FileName: FriendDomainServiceImpl
 * Description: 好友领域服务实现
 */
@Service
public class FriendDomainServiceImpl extends ServiceImpl<FriendRepository, Friend> implements FriendDomainService {
    private final Logger logger = LoggerFactory.getLogger(FriendDomainServiceImpl.class);
    @Autowired
    private MessageEventSenderService messageEventSenderService;

    @Value("${message.mq.event.type}")
    private String eventType;

    @Override
    public List<Long> getFriendIdList(Long userId) {
        return baseMapper.getFriendIdList(userId);
    }

    @Override
    public List<FriendVO> findFriendByUserId(Long userId) {
        if (userId == null) {
            throw new IMException(HttpCode.PARAMS_ERROR);
        }
        return baseMapper.getFriendVOList(userId);
    }

    @Override
    public Boolean isFriend(Long userId1, Long userId2) {
        if (userId1 == null || userId2 == null) {
            throw new IMException(HttpCode.PARAMS_ERROR);
        }
        return baseMapper.checkFriend(userId2, userId1) != null;
    }

    @Override
    public void bindFriend(FriendCommand friendCommand, String headImg, String nickName) {
        if (friendCommand == null || friendCommand.isEmpty()) {
            throw new IMException(HttpCode.PARAMS_ERROR);
        }
        boolean result = false;
        Integer checkStatus = baseMapper.checkFriend(friendCommand.getFriendId(), friendCommand.getUserId());
        if (checkStatus == null) {
            Friend friend = new Friend();
            friend.setId(SnowFlakeFactory.getSnowFlakeFromCache().nextId());
            friend.setUserId(friendCommand.getUserId());
            friend.setFriendId(friendCommand.getFriendId());
            friend.setFriendHeadImage(headImg);
            friend.setFriendNickName(nickName);
            friend.setCreatedTime(new Date());
            result = this.save(friend);
        }
        if (result) {
            //发布领域事件
            IMFriendEvent friendEvent = new IMFriendEvent(friendCommand.getUserId(), friendCommand.getFriendId(), IMPlatformConstants.FRIEND_HANDLER_BIND, this.getTopicEvent());
                messageEventSenderService.send(friendEvent);
        }
    }

    @Override
    public void unbindFriend(FriendCommand friendCommand) {
        if (friendCommand == null || friendCommand.isEmpty()) {
            throw new IMException(HttpCode.PARAMS_ERROR);
        }
        int count = baseMapper.deleteFriend(friendCommand.getFriendId(), friendCommand.getUserId());
        if (count > 0) {
            //发布领域事件
            IMFriendEvent friendEvent = new IMFriendEvent(friendCommand.getUserId(), friendCommand.getFriendId(), IMPlatformConstants.FRIEND_HANDLER_UNBIND, this.getTopicEvent());
            messageEventSenderService.send(friendEvent);
        }
    }

    @Override
    public void update(FriendVO vo, Long userId) {
        int count = baseMapper.updateFriend(vo.getHeadImage(), vo.getNickName(), vo.getId(), userId);
        if (count > 0) {
            //发布领域事件
            IMFriendEvent friendEvent = new IMFriendEvent(userId, vo.getId(), IMPlatformConstants.FRIEND_HANDLER_UPDATE, this.getTopicEvent());
            messageEventSenderService.send(friendEvent);
        }
    }

    @Override
    public FriendVO findFriend(FriendCommand friendCommand) {
        if (friendCommand == null || friendCommand.isEmpty()) {
            throw new IMException(HttpCode.PARAMS_ERROR);
        }
        return baseMapper.getFriendVO(friendCommand.getFriendId(), friendCommand.getUserId());
    }

    @Override
    public List<Friend> getFriendByUserId(Long userId) {
        return baseMapper.getFriendByUserId(userId);
    }

    @Override
    public int updateFriendByFriendId(String headImage, String nickName, Long friendId) {
        return baseMapper.updateFriendByFriendId(headImage, nickName, friendId);
    }

    /**
     * 获取主题事件
     */
    private String getTopicEvent() {
        return IMPlatformConstants.EVENT_PUBLISH_TYPE_ROCKETMQ.equals(eventType) ? IMPlatformConstants.TOPIC_EVENT_ROCKETMQ_FRIEND : IMPlatformConstants.TOPIC_EVENT_COLA;
    }
}
