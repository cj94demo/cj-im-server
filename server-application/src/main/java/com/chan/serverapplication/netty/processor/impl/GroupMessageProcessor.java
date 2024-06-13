package com.chan.serverapplication.netty.processor.impl;

import com.chan.serverapplication.netty.cache.UserChannelContextCache;
import com.chan.serverapplication.netty.processor.MessageProcessor;
import com.chan.serverdomain.enums.IMCmdType;
import com.chan.serverdomain.enums.IMSendCode;
import com.chan.serverdomain.model.IMReceiveInfo;
import com.chan.serverdomain.model.IMSendInfo;
import com.chan.serverdomain.model.IMUserInfo;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/13 21:25
 * FileName: GroupMessageProcessor
 * Description: 群消息处理器
 */
@Component
public class GroupMessageProcessor extends BaseMessageProcessor implements MessageProcessor<IMReceiveInfo> {
    private final Logger logger = LoggerFactory.getLogger(GroupMessageProcessor.class);

    @Async
    @Override
    public void process(IMReceiveInfo receiveInfo) {
        IMUserInfo sender = receiveInfo.getSender();
        List<IMUserInfo> receivers = receiveInfo.getReceivers();
        logger.info("GroupMessageProcessor.process|接收到群消息,发送消息用户:{}，接收消息用户数量:{}，消息内容:{}", sender.getUserId(), receivers.size(), receiveInfo.getData());
        receivers.forEach((receiver) -> {
            try {
                ChannelHandlerContext channelHandlerCtx = UserChannelContextCache.getChannelCtx(receiver.getUserId(), receiver.getTerminal());
                if (channelHandlerCtx != null) {
                    //向用户推送消息
                    IMSendInfo<?> imSendInfo = new IMSendInfo<>(IMCmdType.GROUP_MESSAGE.getCode(), receiveInfo.getData());
                    channelHandlerCtx.writeAndFlush(imSendInfo);
                    //发送确认消息
                    sendGroupMessageResult(receiveInfo, receiver, IMSendCode.SUCCESS);
                } else {
                    //未找到用户的连接信息
                    sendGroupMessageResult(receiveInfo, receiver, IMSendCode.NOT_FIND_CHANNEL);
                    logger.error("GroupMessageProcessor.process|未找到Channel,发送者:{}, 接收者:{}, 消息内容:{}", sender.getUserId(), receiver.getUserId(), receiveInfo.getData());
                }
            } catch (Exception e) {
                sendGroupMessageResult(receiveInfo, receiver, IMSendCode.UNKNOWN_ERROR);
                logger.error("GroupMessageProcessor.process|发送消息异常,发送者:{}, 接收者:{}, 消息内容:{}, 异常信息:{}", sender.getUserId(), receiver.getUserId(), receiveInfo.getData(), e.getMessage());
            }
        });
    }

}
