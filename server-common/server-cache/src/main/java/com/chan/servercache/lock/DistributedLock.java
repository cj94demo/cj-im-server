package com.chan.servercache.lock;

import java.util.concurrent.TimeUnit;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/7 15:56
 * FileName: DistributedLock
 * Description: 分布式锁接口
 */
public interface DistributedLock {
    boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException;

    boolean tryLock(long waitTime, TimeUnit unit) throws InterruptedException;

    boolean tryLock() throws InterruptedException;

    void lock(long leaseTime, TimeUnit unit);

    void unlock();

    boolean isLocked();

    boolean isHeldByThread(long threadId);

    boolean isHeldByCurrentThread();

}
