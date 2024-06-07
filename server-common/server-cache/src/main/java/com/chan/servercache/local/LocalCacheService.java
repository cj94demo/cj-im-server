package com.chan.servercache.local;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/7 15:33
 * FileName: LocalCacheService
 * Description: 本地缓存接口
 */
public interface LocalCacheService<K,V> {
    /**
     * 向缓存中添加数据
     * @param key 缓存的key
     * @param value 缓存的value
     */
    void put(K key, V value);

    /**
     * 根据key从缓存中查询数据
     * @param key 缓存的key
     * @return 缓存的value值
     */
    V getIfPresent(Object key);

    /**
     * 移除缓存中的数据
     * @param key 缓存的key
     */
    void remove(K key);

}
