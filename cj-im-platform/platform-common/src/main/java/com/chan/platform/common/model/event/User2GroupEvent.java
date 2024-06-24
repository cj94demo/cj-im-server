package com.chan.platform.common.model.event;

import com.chan.serverdomain.event.IMBaseEvent;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/24 10:54
 * FileName: User2GroupEvent
 * Description: 用户微服务向群组微服务发送的事件消息
 */
public class User2GroupEvent extends IMBaseEvent {
    //用户头像缩略图
    private String headImageThumb;

    public User2GroupEvent() {
    }

    public User2GroupEvent(Long id, String headImageThumb, String destination) {
        super(id, destination);
        this.headImageThumb = headImageThumb;
    }

    public String getHeadImageThumb() {
        return headImageThumb;
    }

    public void setHeadImageThumb(String headImageThumb) {
        this.headImageThumb = headImageThumb;
    }
}
