package com.chan.serverdomain.model;

import java.util.List;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/6 20:57
 * FileName: IMReceiveInfo
 * Description: 通用接受数据模型
 */
public class IMReceiveInfo extends TopicMessage {
    private static final long serialVersionUID = 1L;
    private Integer cmd;
    private IMUserInfo sender;
    List<IMUserInfo> receivers;
    private Boolean sendResult;
    private Object data;

    public IMReceiveInfo() {
    }

    public IMReceiveInfo(Integer cmd, IMUserInfo sender, List<IMUserInfo> receivers, Boolean sendResult, Object data) {
        this.cmd = cmd;
        this.sender = sender;
        this.receivers = receivers;
        this.sendResult = sendResult;
        this.data = data;
    }

    public Integer getCmd() {
        return cmd;
    }

    public void setCmd(Integer cmd) {
        this.cmd = cmd;
    }

    public IMUserInfo getSender() {
        return sender;
    }

    public void setSender(IMUserInfo sender) {
        this.sender = sender;
    }

    public List<IMUserInfo> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<IMUserInfo> receivers) {
        this.receivers = receivers;
    }

    public Boolean getSendResult() {
        return sendResult;
    }

    public void setSendResult(Boolean sendResult) {
        this.sendResult = sendResult;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
