package com.chan.platform.friend.domain.model.command;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/24 21:08
 * FileName: FriendCommand
 * Description: 好友关系
 */
public class FriendCommand {
    private Long userId;
    private Long friendId;

    public FriendCommand() {
    }

    public FriendCommand(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public boolean isEmpty() {
        return userId == null || friendId == null;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }
}
