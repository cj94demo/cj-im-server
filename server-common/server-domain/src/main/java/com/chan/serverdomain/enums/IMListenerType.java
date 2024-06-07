package com.chan.serverdomain.enums;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/6 21:12
 * FileName: IMListenType
 * Description: 监听消息的类型
 */
public enum IMListenerType {
    ALL(0, "全部消息"),
    PRIVATE_MESSAGE(1, "私聊消息"),
    GROUP_MESSAGE(2, "群聊消息");
    private Integer code;
    private String desc;

    IMListenerType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }
}
