package com.chan.platform.common.session;

import com.chan.serverdomain.model.IMSessionInfo;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/18 10:21
 * FileName: UserSession
 * Description: 用户Session信息
 */
public class UserSession extends IMSessionInfo {
    /*
     * 用户名称
     */
    private String userName;

    /*
     * 用户昵称
     */
    private String nickName;

    public UserSession() {
    }

    public UserSession(Long userId, Integer terminal, String userName, String nickName) {
        super(userId, terminal);
        this.userName = userName;
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
