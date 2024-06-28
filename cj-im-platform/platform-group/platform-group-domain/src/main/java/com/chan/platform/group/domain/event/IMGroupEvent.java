package com.chan.platform.group.domain.event;

import com.chan.serverdomain.event.IMBaseEvent;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/28 17:19
 * FileName: IMGroupEvent
 * Description:
 */
public class IMGroupEvent extends IMBaseEvent {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 操作类型
     */
    private String handler;

    public IMGroupEvent() {
    }

    public IMGroupEvent(Long id, Long userId, String handler, String destination) {
        super(id, destination);
        this.userId = userId;
        this.handler = handler;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }
}
