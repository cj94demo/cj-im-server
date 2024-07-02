package com.chan.sdk.interfaces.sender.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.chan.sdk.infrastructure.multicaster.MessageListenerMultiCaster;
import com.chan.sdk.interfaces.sender.IMSender;
import com.chan.servercache.distribute.DistributedCacheService;
import com.chan.serverdomain.constant.IMConstants;
import com.chan.serverdomain.enums.IMCmdType;
import com.chan.serverdomain.enums.IMListenerType;
import com.chan.serverdomain.enums.IMSendCode;
import com.chan.serverdomain.enums.IMTerminalType;
import com.chan.serverdomain.model.*;
import com.chan.servermq.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/17 10:37
 * FileName: DefaultSender
 * Description: 私聊消息发送器接口实现
 */
@Service
public class DefaultSender implements IMSender {
    @Autowired
    private DistributedCacheService distributedCacheService;

    @Autowired
    private MessageSenderService messageSenderService;

    @Autowired
    private MessageListenerMultiCaster messageListenerMulticaster;

    @Override
    public <T> void sendPrivateMessage(IMPrivateMessage<T> message) {
        if (message == null) {
            return;
        }
        // 向用户的终端发送数据
        List<Integer> receiveTerminals = message.getReceiveTerminals();
        // 终端不为空
        if (CollUtil.isNotEmpty(receiveTerminals)) {
            // 向目标用户发送私聊消息
            this.sendPrivateMessageToTargetUser(message, receiveTerminals);
            // 向自己的其他终端发送私聊消息
            this.sendPrivateMessageToSelf(message, receiveTerminals);
        }
    }

    private <T> void sendPrivateMessageToTargetUser(IMPrivateMessage<T> message, List<Integer> receiveTerminals) {
        receiveTerminals.forEach((receiveTerminal) -> {
            // 获取接收消息的用户的channelId
            String redisKey = String.join(IMConstants.REDIS_KEY_SPLIT, IMConstants.IM_USER_SERVER_ID, message.getReceiveId().toString(),
                    receiveTerminal.toString());
            String serverId = distributedCacheService.get(redisKey);
            // 用户在线，将消息推送到RocketMQ
            if (StrUtil.isNotBlank(serverId)) {
                String sendKey = String.join(IMConstants.MESSAGE_KEY_SPLIT, IMConstants.IM_MESSAGE_PRIVATE_QUEUE, serverId);
                IMReceiveInfo imReceiveInfo = new IMReceiveInfo(IMCmdType.PRIVATE_MESSAGE.getCode(), message.getSender(), Collections.singletonList(new IMUserInfo(message.getReceiveId(), receiveTerminal)),
                        message.getSendResult(), message.getData());
                // 设置发送的主题
                imReceiveInfo.setDestination(sendKey);
                // 发送消息
                messageSenderService.send(imReceiveInfo);
            } else if (BooleanUtil.isTrue(message.getSendResult())) {
                // 回复消息的状态
                IMSendResult<T> result = new IMSendResult<>(message.getSender(), new IMUserInfo(message.getReceiveId(), receiveTerminal), IMSendCode.NOT_ONLINE.getCode(), message.getData());
                messageListenerMulticaster.multicast(IMListenerType.PRIVATE_MESSAGE, result);
            }
        });
    }

    /**
     * 向自己的其他终端发送消息
     */
    private <T> void sendPrivateMessageToSelf(IMPrivateMessage<T> message, List<Integer> receiveTerminals) {
        // 向自己的其他终端发送消息
        if (BooleanUtil.isTrue(message.getSendToSelf())) {
            receiveTerminals.forEach((receiveTerminal) -> {
                if (!receiveTerminal.equals(message.getSender().getTerminal())) {
                    String redisKey = String.join(IMConstants.REDIS_KEY_SPLIT, IMConstants.IM_USER_SERVER_ID, message.getSender().getUserId().toString(),
                            receiveTerminal.toString());
                    String serverId = distributedCacheService.get(redisKey);
                    if (StrUtil.isNotBlank(serverId)) {
                        String sendKey = String.join(IMConstants.MESSAGE_KEY_SPLIT, IMConstants.IM_MESSAGE_PRIVATE_QUEUE, serverId);
                        IMReceiveInfo imReceiveInfo = new IMReceiveInfo(IMCmdType.PRIVATE_MESSAGE.getCode(), message.getSender(),
                                Collections.singletonList(new IMUserInfo(message.getSender().getUserId(), receiveTerminal)),
                                false, message.getData());
                        // 设置发送的主题
                        imReceiveInfo.setDestination(sendKey);
                        // 发送消息
                        messageSenderService.send(imReceiveInfo);
                    }
                }
            });
        }
    }

    @Override
    public <T> void sendGroupMessage(IMGroupMessage<T> message) {
        Map<String, IMUserInfo> userTerminalGroup = this.getUserTerminalGroup(message);
        //分组数据为空，直接返回
        if (CollUtil.isEmpty(userTerminalGroup)) {
            return;
        }
        //从Redis批量拉取数据
        List<String> serverIdList = distributedCacheService.multiGet(userTerminalGroup.keySet());
        if (CollUtil.isEmpty(serverIdList)) {
            return;
        }
        //将接收方按照服务Id进行分组，Key-服务ID，Value-接收消息的用户列表
        Map<Integer, List<IMUserInfo>> serverMap = new HashMap<>();
        //离线用户列表
        List<IMUserInfo> offlineUserList = new LinkedList<>();
        int idx = 0;
        for (Map.Entry<String, IMUserInfo> entry : userTerminalGroup.entrySet()) {
            String serverIdStr = serverIdList.get(idx++);
            if (!StrUtil.isEmpty(serverIdStr)) {
                List<IMUserInfo> list = serverMap.computeIfAbsent(Integer.parseInt(serverIdStr), o -> new LinkedList<>());
                list.add(entry.getValue());
            } else {
                //添加离线用户
                offlineUserList.add(entry.getValue());
            }
        }
        //向群组其他成员发送消息
        this.sendGroupMessageToOtherUsers(serverMap, offlineUserList, message);
        //推送给自己的其他终端
        this.sendGroupMessageToSelf(message);
    }

    /**
     * 推送给自己的其他终端
     */
    private <T> void sendGroupMessageToSelf(IMGroupMessage<T> message) {
        for (Integer terminal : IMTerminalType.codes()) {
            //向不是发消息的终端推送消息
            if (!terminal.equals(message.getSender().getTerminal())) {
                // 获取连接终端的channelId
                String redisKey = String.join(IMConstants.REDIS_KEY_SPLIT, IMConstants.IM_USER_SERVER_ID, message.getSender().getUserId().toString(), terminal.toString());
                String serverId = distributedCacheService.get(redisKey);
                if (!StrUtil.isEmpty(serverId)) {
                    IMReceiveInfo imReceiveInfo = new IMReceiveInfo(IMCmdType.GROUP_MESSAGE.getCode(), message.getSender(), Collections.singletonList(new IMUserInfo(message.getSender().getUserId(), terminal)), false, message.getData());
                    String sendKey = String.join(IMConstants.MESSAGE_KEY_SPLIT, IMConstants.IM_MESSAGE_GROUP_QUEUE, serverId);
                    imReceiveInfo.setDestination(sendKey);
                    messageSenderService.send(imReceiveInfo);
                }
            }
        }
    }

    /**
     * 向群组其他成员发送消息
     */
    private <T> void sendGroupMessageToOtherUsers(Map<Integer, List<IMUserInfo>> serverMap, List<IMUserInfo> offlineUserList, IMGroupMessage<T> message) {
        for (Map.Entry<Integer, List<IMUserInfo>> entry : serverMap.entrySet()) {
            IMReceiveInfo imReceiveInfo = new IMReceiveInfo(IMCmdType.GROUP_MESSAGE.getCode(), message.getSender(), new LinkedList<>(entry.getValue()), message.getSendResult(), message.getData());
            String sendKey = String.join(IMConstants.MESSAGE_KEY_SPLIT, IMConstants.IM_MESSAGE_GROUP_QUEUE, entry.getKey().toString());
            imReceiveInfo.setDestination(sendKey);
            messageSenderService.send(imReceiveInfo);
        }
        //回复离线用户消息状态
        if (message.getSendResult()) {
            offlineUserList.forEach((offlineUser) -> {
                IMSendResult<T> result = new IMSendResult<>(message.getSender(), offlineUser, IMSendCode.NOT_ONLINE.getCode(), message.getData());
                messageListenerMulticaster.multicast(IMListenerType.GROUP_MESSAGE, result);
            });
        }
    }

    /**
     * 获取用户终端分组信息
     */
    private <T> Map<String, IMUserInfo> getUserTerminalGroup(IMGroupMessage<T> message) {
        Map<String, IMUserInfo> map = new HashMap<>();
        if (message == null) {
            return map;
        }
        for (Integer terminal : message.getReceiveTerminals()) {
            message.getReceiveIds().forEach((receiveId) -> {
                String key = String.join(IMConstants.REDIS_KEY_SPLIT, IMConstants.IM_USER_SERVER_ID, receiveId.toString(), terminal.toString());
                map.put(key, new IMUserInfo(receiveId, terminal));
            });
        }
        return map;
    }

    @Override
    public Map<Long, List<IMTerminalType>> getOnlineTerminal(List<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyMap();
        }
        Map<String, IMUserInfo> userMap = new HashMap<>();
        for (Long userId : userIds) {
            for (Integer terminal : IMTerminalType.codes()) {
                String key = String.join(IMConstants.REDIS_KEY_SPLIT, IMConstants.IM_USER_SERVER_ID, userId.toString(), terminal.toString());
                userMap.put(key, new IMUserInfo(userId, terminal));
            }
        }
        //从Redis批量获取数据
        List<String> serverIdList = distributedCacheService.multiGet(userMap.keySet());
        int idx = 0;
        Map<Long, List<IMTerminalType>> onlineMap = new HashMap<>();
        for (Map.Entry<String, IMUserInfo> entry : userMap.entrySet()) {
            if (!StrUtil.isEmpty(serverIdList.get(idx++))) {
                IMUserInfo imUserInfo = entry.getValue();
                List<IMTerminalType> imTerminalTypeList = onlineMap.computeIfAbsent(imUserInfo.getUserId(), o -> new LinkedList<>());
                imTerminalTypeList.add(IMTerminalType.fromCode(imUserInfo.getTerminal()));
            }
        }
        return onlineMap;
    }

    @Override
    public Boolean isOnline(Long userId) {
        String redisKey = String.join(IMConstants.REDIS_KEY_SPLIT, IMConstants.IM_USER_SERVER_ID, userId.toString(), "*");
        Set<String> keys = distributedCacheService.keys(redisKey);
        return CollUtil.isNotEmpty(keys);
    }

    @Override
    public List<Long> getOnlineUser(List<Long> userIds) {
        return new LinkedList<>(this.getOnlineTerminal(userIds).keySet());
    }

}
