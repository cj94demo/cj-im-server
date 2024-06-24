package com.chan.platform.friend.domain.event;

import com.chan.serverdomain.event.IMBaseEvent;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/24 21:06
 * FileName: IMFriendEvent
 * Description: 好友事件模型
 */
public class IMFriendEvent extends IMBaseEvent {
    //操作
    private String handler;

    //好友id
    private Long friendId;

    public IMFriendEvent() {
    }

    public IMFriendEvent(Long id, Long friendId, String handler, String destination) {
        super(id, destination);
        this.handler = handler;
        this.friendId = friendId;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }
}
