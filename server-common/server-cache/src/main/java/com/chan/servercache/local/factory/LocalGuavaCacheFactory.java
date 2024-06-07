package com.chan.servercache.local.factory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/7 15:36
 * FileName: LocalGuavaCacheFactory
 * Description: 基于Guava的本地缓存工厂类
 */
public class LocalGuavaCacheFactory {
    public static <K, V> Cache<K, V> getLocalCache() {
        return CacheBuilder.newBuilder().initialCapacity(200).concurrencyLevel(5).expireAfterWrite(300, TimeUnit.SECONDS).build();
    }

    public static <K, V> Cache<K, V> getLocalCache(long duration) {
        return CacheBuilder.newBuilder().initialCapacity(200).concurrencyLevel(5).expireAfterWrite(duration, TimeUnit.SECONDS).build();
    }

    public static <K, V> Cache<K, V> getLocalCache(int initialCapacity, long duration) {
        return CacheBuilder.newBuilder().initialCapacity(initialCapacity).concurrencyLevel(5).expireAfterWrite(duration, TimeUnit.SECONDS).build();
    }
}
