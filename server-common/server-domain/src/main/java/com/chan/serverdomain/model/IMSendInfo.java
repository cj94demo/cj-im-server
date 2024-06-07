package com.chan.serverdomain.model;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/6 20:52
 * FileName: IMSendInfo
 * Description: 通用发送数据信息模型
 */
public class IMSendInfo<T> {
    private Integer cmd;
    private T data;

    public IMSendInfo() {
    }

    public IMSendInfo(Integer cmd, T data) {
        this.cmd = cmd;
        this.data = data;
    }

    public Integer getCmd() {
        return cmd;
    }

    public void setCmd(Integer cmd) {
        this.cmd = cmd;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
