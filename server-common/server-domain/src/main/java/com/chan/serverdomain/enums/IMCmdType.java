package com.chan.serverdomain.enums;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/6 21:00
 * FileName: IMCmdType
 * Description: 发送消息的类型
 */
public enum IMCmdType {
    LOGIN(0, "登录"),
    HEART_BEAT(1, "心跳"),
    FORCE_LOGOUT(2, "强制下线"),
    PRIVATE_MESSAGE(3, "私聊消息"),
    GROUP_MESSAGE(4, "群发消息");

    private Integer code;
    private String desc;

    IMCmdType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public static IMCmdType fromCode(int code) {
        for (IMCmdType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }
}
