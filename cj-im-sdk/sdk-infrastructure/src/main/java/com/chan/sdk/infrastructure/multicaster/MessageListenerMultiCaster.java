package com.chan.sdk.infrastructure.multicaster;


import com.chan.serverdomain.enums.IMListenerType;
import com.chan.serverdomain.model.IMSendResult;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/17 9:28
 * FileName: MessageListenerMultiCaster
 * Description: 广播处理消息监听器的接口
 */
public interface MessageListenerMultiCaster {
    <T> void multicast(IMListenerType listenerType, IMSendResult<T> result);
}
