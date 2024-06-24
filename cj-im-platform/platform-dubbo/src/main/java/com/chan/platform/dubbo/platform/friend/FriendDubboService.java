package com.chan.platform.dubbo.platform.friend;

import com.chan.platform.common.model.entity.Friend;

import java.util.List;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/24 10:45
 * FileName: FriendDubboService
 * Description: 用户Dubbo服务
 */
public interface FriendDubboService {
    /**
     * 判断用户2是否用户1的好友
     *
     * @param userId1 用户1的id
     * @param userId2 用户2的id
     * @return true/false
     */
    Boolean isFriend(Long userId1, Long userId2);

    /**
     * 根据用户id获取好友的id列表
     */
    List<Long> getFriendIdList(Long userId);

    /**
     * 根据用户id获取好友列表
     */
    List<Friend> getFriendByUserId(Long userId);
}
