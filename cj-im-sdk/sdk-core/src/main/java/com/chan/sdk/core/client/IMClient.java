package com.chan.sdk.core.client;

import com.chan.serverdomain.enums.IMTerminalType;
import com.chan.serverdomain.model.IMGroupMessage;
import com.chan.serverdomain.model.IMPrivateMessage;

import java.util.List;
import java.util.Map;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/17 15:25
 * FileName: IMClient
 * Description: IM客户端
 */
public interface IMClient {
    /**
     * 发送私聊消息
     */
    <T> void sendPrivateMessage(IMPrivateMessage<T> message);

    /**
     * 发送群消息
     */
    <T> void sendGroupMessage(IMGroupMessage<T> message);

    /**
     * 判断用户是否在线
     */
    Boolean isOnline(Long userId);

    /**
     * 筛选出在线的用户
     */
    List<Long> getOnlineUserList(List<Long> userIds);

    /**
     * 获取用户与其在线的终端列表
     */
    Map<Long, List<IMTerminalType>> getOnlineTerminal(List<Long> userIds);

}

