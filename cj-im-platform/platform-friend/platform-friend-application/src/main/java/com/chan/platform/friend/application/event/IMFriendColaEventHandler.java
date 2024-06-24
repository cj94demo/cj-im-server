package com.chan.platform.friend.application.event;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.event.EventHandler;
import com.alibaba.cola.event.EventHandlerI;
import com.alibaba.fastjson2.JSON;
import com.chan.platform.friend.application.cache.FriendCacheService;
import com.chan.platform.friend.domain.event.IMFriendEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/24 21:05
 * FileName: IMFriendColaEventHandler
 * Description: 基于Cola的用户事件处理器
 */
@EventHandler
@ConditionalOnProperty(name = "message.mq.event.type", havingValue = "cola")
public class IMFriendColaEventHandler implements EventHandlerI<Response, IMFriendEvent> {
    private final Logger logger = LoggerFactory.getLogger(IMFriendColaEventHandler.class);

    @Autowired
    private FriendCacheService friendCacheService;

    @Override
    public Response execute(IMFriendEvent imFriendEvent) {
        if (imFriendEvent == null || imFriendEvent.getId() == null) {
            logger.info("cola|friendEvent|接收好友事件参数错误");
            return Response.buildSuccess();
        }
        logger.info("cola|friendEvent|接收好友事件|{}", JSON.toJSON(imFriendEvent));
        friendCacheService.updateFriendCache(imFriendEvent);
        return Response.buildSuccess();
    }
}
