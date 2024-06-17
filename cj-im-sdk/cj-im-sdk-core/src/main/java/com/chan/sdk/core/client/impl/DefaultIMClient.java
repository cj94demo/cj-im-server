package com.chan.sdk.core.client.impl;

import com.chan.sdk.core.client.IMClient;
import com.chan.sdk.interfaces.sender.IMSender;
import com.chan.serverdomain.enums.IMTerminalType;
import com.chan.serverdomain.model.IMGroupMessage;
import com.chan.serverdomain.model.IMPrivateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/17 15:26
 * FileName: DefaultIMClient
 * Description: 默认的客户端实现
 */
@Service
public class DefaultIMClient implements IMClient {
    @Autowired
    private IMSender imSender;

    @Override
    public <T> void sendPrivateMessage(IMPrivateMessage<T> message) {
        imSender.sendPrivateMessage(message);
    }

    @Override
    public <T> void sendGroupMessage(IMGroupMessage<T> message) {
        imSender.sendGroupMessage(message);
    }

    @Override
    public Boolean isOnline(Long userId) {
        return imSender.isOnline(userId);
    }

    @Override
    public List<Long> getOnlineUserList(List<Long> userIds) {
        return imSender.getOnlineUser(userIds);
    }

    @Override
    public Map<Long, List<IMTerminalType>> getOnlineTerminal(List<Long> userIds) {
        return imSender.getOnlineTerminal(userIds);
    }

}
