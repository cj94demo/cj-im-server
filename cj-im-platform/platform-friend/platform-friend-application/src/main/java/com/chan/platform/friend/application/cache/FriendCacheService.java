package com.chan.platform.friend.application.cache;

import com.chan.platform.friend.domain.event.IMFriendEvent;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/24 21:01
 * FileName: FriendCacheService
 * Description: 好友缓存服务
 */
public interface FriendCacheService {
    /**
     * 更新好友缓存
     */
    void updateFriendCache(IMFriendEvent friendEvent);
}
