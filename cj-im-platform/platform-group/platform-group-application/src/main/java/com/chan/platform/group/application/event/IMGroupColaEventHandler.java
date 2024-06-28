package com.chan.platform.group.application.event;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.event.EventHandler;
import com.alibaba.cola.event.EventHandlerI;
import com.alibaba.fastjson2.JSON;
import com.chan.platform.group.application.cache.GroupCacheService;
import com.chan.platform.group.domain.event.IMGroupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/28 17:39
 * FileName: IMGroupColaEventHandler
 * Description: 基于Cola的用户事件处理器
 */
@EventHandler
@ConditionalOnProperty(name = "message.mq.event.type", havingValue = "cola")
public class IMGroupColaEventHandler implements EventHandlerI<Response, IMGroupEvent> {
    private final Logger logger = LoggerFactory.getLogger(IMGroupColaEventHandler.class);

    @Autowired
    private GroupCacheService groupCacheService;

    @Override
    public Response execute(IMGroupEvent imGroupEvent) {
        if (imGroupEvent == null || imGroupEvent.getId() == null) {
            logger.info("cola|groupEvent|接收群组事件参数错误");
            return Response.buildSuccess();
        }
        logger.info("cola|groupEvent|接收群组事件参数|{}", JSON.toJSON(imGroupEvent));
        groupCacheService.updateGroupCache(imGroupEvent);
        return Response.buildSuccess();
    }
}
