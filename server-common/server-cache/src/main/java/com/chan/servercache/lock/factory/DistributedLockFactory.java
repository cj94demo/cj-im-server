package com.chan.servercache.lock.factory;

import com.chan.servercache.lock.DistributedLock;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/7 15:43
 * FileName: DistributedLockFactory
 * Description: 分布式锁工程接口
 */
public interface DistributedLockFactory {
    /**
     * 根据key获取分布式锁
     */
    DistributedLock getDistributedLock(String key);


}
