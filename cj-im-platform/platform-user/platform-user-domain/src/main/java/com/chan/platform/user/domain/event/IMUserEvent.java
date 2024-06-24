package com.chan.platform.user.domain.event;

import com.chan.serverdomain.event.IMBaseEvent;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/24 11:00
 * FileName: IMUserEvent
 * Description: 用户领域事件
 */
public class IMUserEvent extends IMBaseEvent {
    private String userName;

    public IMUserEvent(Long id, String userName, String destination) {
        super(id, destination);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
