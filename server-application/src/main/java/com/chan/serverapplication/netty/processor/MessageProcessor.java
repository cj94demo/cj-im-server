package com.chan.serverapplication.netty.processor;

import io.netty.channel.ChannelHandlerContext;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/13 21:19
 * FileName: MessageProcessor
 * Description: 消息处理器接口
 */
public interface MessageProcessor<T> {
    /**
     * 处理数据
     */
    default void process(ChannelHandlerContext ctx, T data) {

    }

    /**
     * 处理数据
     */
    default void process(T data) {

    }

    /**
     * 转化数据
     */
    default T transForm(Object obj) {
        return (T) obj;
    }
}
