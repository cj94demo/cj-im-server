package com.chan.servercache.model;

import com.chan.servercache.model.base.IMCommonCache;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/7 16:01
 * FileName: IMBusinessCache
 * Description: 业务数据缓存
 */
public class IMBusinessCache<T> extends IMCommonCache {
    private T data;

    public IMBusinessCache<T> with(T data) {
        this.data = data;
        this.exist = true;
        return this;
    }

    public IMBusinessCache<T> withVersion(Long version) {
        this.version = version;
        return this;
    }

    public IMBusinessCache<T> retryLater() {
        this.retryLater = true;
        return this;
    }

    public IMBusinessCache<T> notExist() {
        this.exist = false;
        this.version = -1L;
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
