package com.chan.servercache.distribute.data;

import java.time.LocalDateTime;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/7 15:40
 * FileName: RedisData
 * Description: 缓存到Redis中的数据，主要配合使用数据的逻辑过期
 */
public class RedisData {
    //实际业务数据
    private Object data;
    //过期时间点
    private LocalDateTime expireTime;

    public RedisData() {
    }

    public RedisData(Object data, LocalDateTime expireTime) {
        this.data = data;
        this.expireTime = expireTime;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

}
