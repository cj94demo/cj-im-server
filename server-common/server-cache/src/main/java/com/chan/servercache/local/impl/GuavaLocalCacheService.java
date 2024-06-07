package com.chan.servercache.local.impl;

import com.chan.servercache.local.LocalCacheService;
import com.chan.servercache.local.factory.LocalGuavaCacheFactory;
import com.google.common.cache.Cache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/7 15:34
 * FileName: GuavaLocalCacheService
 * Description: 基于Guava实现的本地缓存
 */
@Component
@ConditionalOnProperty(name = "local.cache.type", havingValue = "guava")
public class GuavaLocalCacheService<K, V> implements LocalCacheService<K, V> {
    //本地缓存，基于Guava实现
    private final Cache<K, V> cache = LocalGuavaCacheFactory.getLocalCache();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public V getIfPresent(Object key) {
        return cache.getIfPresent(key);
    }

    @Override
    public void remove(K key) {
        cache.invalidate(key);
    }

}
