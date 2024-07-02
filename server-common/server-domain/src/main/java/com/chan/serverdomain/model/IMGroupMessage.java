package com.chan.serverdomain.model;

import com.chan.serverdomain.enums.IMTerminalType;

import java.util.LinkedList;
import java.util.List;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/6 20:50
 * FileName: IMGroupMessage
 * Description: 群聊数据信息模型
 */
public class IMGroupMessage<T> {
    private IMUserInfo sender;
    private List<Long> receiveIds = new LinkedList<>();
    private List<Integer> receiveTerminals = IMTerminalType.codes();
    private Boolean sendToSelf = true;
    private Boolean sendResult = true;
    private T data;

    public IMGroupMessage() {
    }

    public IMGroupMessage(IMUserInfo sender, List<Long> receiveIds, List<Integer> receiveTerminals, Boolean sendToSelf, Boolean sendResult, T data) {
        this.sender = sender;
        this.receiveIds = receiveIds;
        this.receiveTerminals = receiveTerminals;
        this.sendToSelf = sendToSelf;
        this.sendResult = sendResult;
        this.data = data;
    }

    public IMUserInfo getSender() {
        return sender;
    }

    public void setSender(IMUserInfo sender) {
        this.sender = sender;
    }

    public List<Long> getReceiveIds() {
        return receiveIds;
    }

    public void setReceiveIds(List<Long> receiveIds) {
        this.receiveIds = receiveIds;
    }

    public List<Integer> getReceiveTerminals() {
        return receiveTerminals;
    }

    public void setReceiveTerminals(List<Integer> receiveTerminals) {
        this.receiveTerminals = receiveTerminals;
    }

    public Boolean getSendToSelf() {
        return sendToSelf;
    }

    public void setSendToSelf(Boolean sendToSelf) {
        this.sendToSelf = sendToSelf;
    }

    public Boolean getSendResult() {
        return sendResult;
    }

    public void setSendResult(Boolean sendResult) {
        this.sendResult = sendResult;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
