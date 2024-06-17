package com.chan.sdk.domain.listener;

import com.chan.serverdomain.model.IMSendResult;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/17 16:29
 * FileName: MessageListener
 * Description: 消息监听接口
 */
public interface MessageListener<T> {
    /**
     * 处理发送的结果
     */
    void doProcess(IMSendResult<T> result);
}
