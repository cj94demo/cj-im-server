package com.chan.platform.group.application.cache;

import com.chan.platform.group.domain.event.IMGroupEvent;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/28 17:30
 * FileName: GroupCacheService
 * Description: 群组缓存服务
 */
public interface GroupCacheService {
    /**
     * 更新群组缓存
     */
    void updateGroupCache(IMGroupEvent imGroupEvent);
}
