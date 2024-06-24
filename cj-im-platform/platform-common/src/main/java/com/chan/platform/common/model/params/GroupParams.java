package com.chan.platform.common.model.params;

import java.io.Serializable;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/24 10:56
 * FileName: GroupParams
 * Description: 群组Command
 */
public class GroupParams implements Serializable {
    private static final long serialVersionUID = 1231445988907408576L;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 群组id
     */
    private Long groupId;

    public GroupParams() {
    }

    public GroupParams(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public boolean isEmpty() {
        return userId == null || groupId == null;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
