package com.chan.platform.common.model.enums;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/18 9:58
 * FileName: MessageStatus
 * Description: 消息状态
 */
public enum MessageStatus {
    UNSEND(0,"未发送"),
    SENDED(1,"送达"),
    RECALL(2,"撤回"),
    READED(3,"已读");

    private final Integer code;

    private final String desc;

    MessageStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer code(){
        return this.code;
    }
}
