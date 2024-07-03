package com.chan.serverdomain.enums;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/6 21:14
 * FileName: IMSendCode
 * Description: 发送消息的状态
 */
public enum IMSendCode {
    SUCCESS(0, "发送成功"),
    NOT_ONLINE(1, "对方不在线"),
    NOT_FIND_CHANNEL(2, "未找到对方的channel"),
    UNKNOWN_ERROR(3, "未知异常");
    private Integer code;
    private String desc;

    IMSendCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
    }
}
