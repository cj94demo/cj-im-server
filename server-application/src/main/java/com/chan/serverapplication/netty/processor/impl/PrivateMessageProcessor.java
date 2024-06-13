package com.chan.serverapplication.netty.processor.impl;

import com.chan.serverapplication.netty.cache.UserChannelContextCache;
import com.chan.serverapplication.netty.processor.MessageProcessor;
import com.chan.serverdomain.enums.IMCmdType;
import com.chan.serverdomain.enums.IMSendCode;
import com.chan.serverdomain.model.IMReceiveInfo;
import com.chan.serverdomain.model.IMSendInfo;
import com.chan.serverdomain.model.IMUserInfo;
import com.chan.servermq.MessageSenderService;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/13 21:31
 * FileName: PrivateMessageProcessor
 * Description: 私聊消息处理器
 */
public class PrivateMessageProcessor extends BaseMessageProcessor implements MessageProcessor<IMReceiveInfo> {
    private final Logger logger = LoggerFactory.getLogger(PrivateMessageProcessor.class);

    @Autowired
    private MessageSenderService messageSenderService;

    @Override
    public void process(IMReceiveInfo receiveInfo) {
        IMUserInfo sender = receiveInfo.getSender();
        IMUserInfo receiver = receiveInfo.getReceivers().get(0);
        logger.info("PrivateMessageProcessor.process|接收到消息,发送者:{}, 接收者:{}, 内容:{}", sender.getUserId(), receiver.getUserId(), receiveInfo.getData());
        try {
            ChannelHandlerContext channelHandlerContext = UserChannelContextCache.getChannelCtx(receiver.getUserId(), receiver.getTerminal());
            if (channelHandlerContext != null) {
                //推送消息
                IMSendInfo<?> imSendInfo = new IMSendInfo<>(IMCmdType.PRIVATE_MESSAGE.getCode(), receiveInfo.getData());
                channelHandlerContext.writeAndFlush(imSendInfo);
                sendPrivateMessageResult(receiveInfo, IMSendCode.SUCCESS);
            } else {
                sendPrivateMessageResult(receiveInfo, IMSendCode.NOT_FIND_CHANNEL);
                logger.error("PrivateMessageProcessor.process|未找到Channel, 发送者:{}, 接收者:{}, 内容:{}", sender.getUserId(), receiver.getUserId(), receiveInfo.getData());
            }
        } catch (Exception e) {
            sendPrivateMessageResult(receiveInfo, IMSendCode.UNKNOWN_ERROR);
            logger.error("PrivateMessageProcessor.process|发送异常,发送者:{}, 接收者:{}, 内容:{}, 异常信息:{}", sender.getUserId(), receiver.getUserId(), receiveInfo.getData(), e.getMessage());
        }
    }

}
