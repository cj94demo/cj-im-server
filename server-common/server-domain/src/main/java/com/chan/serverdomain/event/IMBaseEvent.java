package com.chan.serverdomain.event;

import com.chan.serverdomain.model.TopicMessage;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/7 15:18
 * FileName: IMBaseEvent
 * Description: IM系统基础事件类
 */
public class IMBaseEvent extends TopicMessage {
    private Long id;

    public IMBaseEvent() {
    }

    public IMBaseEvent(Long id, String destination) {
        super(destination);
        this.id = id;
    }

    public IMBaseEvent(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
