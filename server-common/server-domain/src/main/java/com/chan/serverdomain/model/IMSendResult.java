package com.chan.serverdomain.model;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/6 20:55
 * FileName: IMSendResult
 * Description: 响应结果数据模型
 */
public class IMSendResult<T> extends TopicMessage {
    private static final long serialVersionUID = 1L;
    private IMUserInfo sender;
    private IMUserInfo receiver;
    private Integer code;
    private T data;

    public IMSendResult() {
    }

    public IMSendResult(IMUserInfo sender, IMUserInfo receiver, Integer code, T data) {
        this.sender = sender;
        this.receiver = receiver;
        this.code = code;
        this.data = data;
    }

    public IMUserInfo getSender() {
        return sender;
    }

    public void setSender(IMUserInfo sender) {
        this.sender = sender;
    }

    public IMUserInfo getReceiver() {
        return receiver;
    }

    public void setReceiver(IMUserInfo receiver) {
        this.receiver = receiver;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
