package com.chan.platform.common.model.event;

import com.chan.serverdomain.event.IMBaseEvent;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/24 10:53
 * FileName: User2FriendEvent
 * Description: 用户微服务到好友微服务的事件
 */
public class User2FriendEvent extends IMBaseEvent {
    //昵称
    private String nickName;
    //头像
    private String headImg;

    public User2FriendEvent() {
    }

    public User2FriendEvent(Long id, String nickName, String headImg, String destination) {
        super(id, destination);
        this.nickName = nickName;
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}
