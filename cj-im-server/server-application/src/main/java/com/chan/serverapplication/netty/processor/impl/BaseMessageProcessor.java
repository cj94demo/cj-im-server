package com.chan.serverapplication.netty.processor.impl;

import com.chan.serverdomain.constant.IMConstants;
import com.chan.serverdomain.enums.IMSendCode;
import com.chan.serverdomain.model.IMReceiveInfo;
import com.chan.serverdomain.model.IMSendResult;
import com.chan.serverdomain.model.IMUserInfo;
import com.chan.servermq.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/13 21:22
 * FileName: BaseMessageProcessor
 * Description: 基础消息处理器
 */
public class BaseMessageProcessor {
    @Autowired
    private MessageSenderService messageSenderService;

    protected void sendPrivateMessageResult(IMReceiveInfo receiveInfo, IMSendCode sendCode) {
        if (receiveInfo.getSendResult()) {
            IMSendResult<?> result = new IMSendResult<>(receiveInfo.getSender(), receiveInfo.getReceivers().get(0), sendCode.getCode(), receiveInfo.getData());
            result.setDestination(IMConstants.IM_RESULT_PRIVATE_QUEUE);
            messageSenderService.send(result);
        }
    }

    /**
     * 发送结果数据
     */
    protected void sendGroupMessageResult(IMReceiveInfo imReceivenfo, IMUserInfo imUserInfo, IMSendCode imSendCode) {
        if (imReceivenfo.getSendResult()) {
            IMSendResult<?> imSendResult = new IMSendResult<>(imReceivenfo.getSender(), imUserInfo, imSendCode.getCode(), imReceivenfo.getData());
            imSendResult.setDestination(IMConstants.IM_RESULT_GROUP_QUEUE);
            messageSenderService.send(imSendResult);
        }
    }

}
