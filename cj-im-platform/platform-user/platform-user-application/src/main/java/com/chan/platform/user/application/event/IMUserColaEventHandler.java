package com.chan.platform.user.application.event;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.event.EventHandler;
import com.alibaba.cola.event.EventHandlerI;
import com.alibaba.fastjson.JSON;
import com.chan.platform.user.application.cache.service.UserCacheService;
import com.chan.platform.user.domain.event.IMUserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/24 10:59
 * FileName: IMUserColaEventHandler
 * Description: 基于Cola的用户事件处理器
 */
@EventHandler
@ConditionalOnProperty(name = "message.mq.event.type", havingValue = "cola")
public class IMUserColaEventHandler implements EventHandlerI<Response, IMUserEvent> {
    private final Logger logger = LoggerFactory.getLogger(IMUserColaEventHandler.class);

    @Autowired
    private UserCacheService userCacheService;

    @Override
    public Response execute(IMUserEvent imUserEvent) {
        if (imUserEvent == null || imUserEvent.getId() == null) {
            logger.info("cola|userEvent|接收用户事件参数错误");
            return Response.buildSuccess();
        }
        logger.info("cola|userEvent|接收用户事件|{}", JSON.toJSON(imUserEvent));
        userCacheService.updateUserCache(imUserEvent.getId());
        return Response.buildSuccess();
    }
}
