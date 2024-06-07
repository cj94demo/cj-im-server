package com.chan.serverdomain.model;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/6 20:45
 * FileName: IMUserInfo
 * Description: 用户信息模型
 */
public class IMUserInfo {
    private Long userId;
    private Integer terminal;

    public IMUserInfo() {
    }

    public IMUserInfo(Long userId, Integer terminal) {
        this.userId = userId;
        this.terminal = terminal;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getTerminal() {
        return terminal;
    }

    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }
}
