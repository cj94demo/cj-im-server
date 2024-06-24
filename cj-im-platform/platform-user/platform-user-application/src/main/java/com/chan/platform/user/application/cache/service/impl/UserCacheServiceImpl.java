package com.chan.platform.user.application.cache.service.impl;

import com.chan.platform.common.model.contants.IMPlatformConstants;
import com.chan.platform.common.model.entity.User;
import com.chan.platform.user.application.cache.service.UserCacheService;
import com.chan.platform.user.domain.repository.UserRepository;
import com.chan.servercache.distribute.DistributedCacheService;
import com.chan.servercache.lock.DistributedLock;
import com.chan.servercache.lock.factory.DistributedLockFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/24 10:24
 * FileName: UserCacheServiceImpl
 * Description: 缓存Service实现
 */
@Service
public class UserCacheServiceImpl implements UserCacheService {
    private final Logger logger = LoggerFactory.getLogger(UserCacheServiceImpl.class);
    @Autowired
    private DistributedCacheService distributedCacheService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DistributedLockFactory distributedLockFactory;

    @Override
    public void updateUserCache(Long userId) {
        if (userId == null) {
            logger.info("IMUserCache|更新分布式缓存时，用户id为空");
            return;
        }
        logger.info("IMUserCache|更新分布式缓存|{}", userId);
        //获取分布式锁，保证只有一个线程在更新分布式缓存
        DistributedLock lock = distributedLockFactory.getDistributedLock(IMPlatformConstants.IM_USER_UPDATE_CACHE_LOCK_KEY.concat(String.valueOf(userId)));
        try {
            boolean isSuccess = lock.tryLock();
            if (!isSuccess) {
                return;
            }
            //只有获取到锁的线程才会向下执行
            User user = userRepository.selectById(userId);
            if (user == null) {
                return;
            }

            //更新用户id的缓存数据
            String userIdKey = distributedCacheService.getKey(IMPlatformConstants.PLATFORM_REDIS_USER_KEY, userId);
            distributedCacheService.set(userIdKey, user, IMPlatformConstants.DEFAULT_REDIS_CACHE_EXPIRE_TIME, TimeUnit.MINUTES);

            //更新用户名的缓存数据
            String userNameKey = distributedCacheService.getKey(IMPlatformConstants.PLATFORM_REDIS_USER_KEY, user.getUserName());
            distributedCacheService.set(userNameKey, user, IMPlatformConstants.DEFAULT_REDIS_CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        } catch (Exception e) {
            logger.error("IMUserCache|更新分布式缓存失败|{}", userId);
        } finally {
            lock.unlock();
        }
    }
}
