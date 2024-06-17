package com.chan.sdk.infrastructure.multicaster.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.chan.sdk.domain.annotation.IMListener;
import com.chan.sdk.domain.listener.MessageListener;
import com.chan.sdk.infrastructure.multicaster.MessageListenerMultiCaster;
import com.chan.serverdomain.enums.IMListenerType;
import com.chan.serverdomain.model.IMSendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/17 16:31
 * FileName: DefaultMessageListenerMulticaster
 * Description: 消息广播默认实现类
 */
@Component
public class DefaultMessageListenerMulticaster implements MessageListenerMultiCaster {
    @Autowired(required = false)
    private List<MessageListener> messageListenerList = Collections.emptyList();

    @Override
    public <T> void multicast(IMListenerType listenerType, IMSendResult<T> result) {
        //为空，直接返回
        if (CollUtil.isEmpty(messageListenerList)) {
            return;
        }
        messageListenerList.forEach((messageListener) -> {
            IMListener imListener = messageListener.getClass().getAnnotation(IMListener.class);
            if (imListener != null && (IMListenerType.ALL.equals(imListener.listenerType()) || imListener.listenerType().equals(listenerType))) {
                if (result.getData() instanceof JSONObject) {
                    Type superInterface = messageListener.getClass().getGenericInterfaces()[0];
                    Type type = ((ParameterizedType) superInterface).getActualTypeArguments()[0];
                    JSONObject data = (JSONObject) result.getData();
                    result.setData(data.toJavaObject(type));
                }
                messageListener.doProcess(result);
            }
        });
    }

}
