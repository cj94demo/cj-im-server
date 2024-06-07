package com.chan.servercache.model.base;

import java.io.Serializable;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/7 16:02
 * FileName: IMCommonCache
 * Description: 通用缓存模型
 */
public class IMCommonCache implements Serializable {
    private static final long serialVersionUID = 2448735813082442223L;
    //缓存数据是否存在
    protected boolean exist;
    //缓存版本号
    protected Long version;
    //稍后再试
    protected boolean retryLater;

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean isRetryLater() {
        return retryLater;
    }

    public void setRetryLater(boolean retryLater) {
        this.retryLater = retryLater;
    }

}
