package com.chan.platform.message.application.consimer;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.chan.platform.common.model.contants.IMPlatformConstants;
import com.chan.platform.common.model.enums.MessageStatus;
import com.chan.platform.common.model.vo.PrivateMessageVO;
import com.chan.platform.common.utils.BeanUtils;
import com.chan.platform.message.domain.event.IMPrivateMessageTxEvent;
import com.chan.sdk.core.client.IMClient;
import com.chan.serverdomain.constant.IMConstants;
import com.chan.serverdomain.model.IMPrivateMessage;
import com.chan.serverdomain.model.IMUserInfo;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/29
 * FileName: IMPrivateMessageRocketMQEventConsumer
 * Description:
 */
@Component
@ConditionalOnProperty(name = "message.mq.type", havingValue = "rocketmq")
@RocketMQMessageListener(consumerGroup = IMPlatformConstants.TOPIC_PRIVATE_TX_MESSAGE_GROUP, topic = IMPlatformConstants.TOPIC_PRIVATE_TX_MESSAGE)
public class IMPrivateMessageRocketMQEventConsumer implements RocketMQListener<String> {
    private final Logger logger = LoggerFactory.getLogger(IMPrivateMessageRocketMQEventConsumer.class);
    @Autowired
    private IMClient imClient;

    @Override
    public void onMessage(String message) {
        if (StrUtil.isEmpty(message)) {
            logger.info("rocketmq|privateMessageTxConsumer|接收消息微服务发送过来的单聊消息事件参数为空");
            return;
        }
        logger.info("rocketmq|privateMessageTxConsumer|接收消息微服务发送过来的单聊消息事件|{}", message);
        IMPrivateMessageTxEvent imPrivateMessageTxEvent = this.getEventMessage(message);
        if (imPrivateMessageTxEvent == null || imPrivateMessageTxEvent.getPrivateMessageDTO() == null) {
            logger.error("rocketmq|privateMessageTxConsumer|接收消息微服务发送过来的单聊消息事件转换失败");
            return;
        }
        PrivateMessageVO privateMessageVO = BeanUtils.copyProperties(imPrivateMessageTxEvent.getPrivateMessageDTO(), PrivateMessageVO.class);
        //设置消息id
        privateMessageVO.setId(imPrivateMessageTxEvent.getId());
        //设置发送者id
        privateMessageVO.setSendId(imPrivateMessageTxEvent.getSenderId());
        //设置状态
        privateMessageVO.setStatus(MessageStatus.UNSEND.code());
        //发送时间
        privateMessageVO.setSendTime(imPrivateMessageTxEvent.getSendTime());
        //封装发送消息数据模型
        IMPrivateMessage<PrivateMessageVO> sendMessage = new IMPrivateMessage<>();
        sendMessage.setSender(new IMUserInfo(privateMessageVO.getSendId(), imPrivateMessageTxEvent.getTerminal()));
        sendMessage.setReceivedId(privateMessageVO.getRecvId());
        sendMessage.setSendToSelf(true);
        sendMessage.setData(privateMessageVO);
        imClient.sendPrivateMessage(sendMessage);
        logger.info("发送私聊消息，发送id:{},接收id:{}，内容:{}", privateMessageVO.getSendId(), privateMessageVO.getRecvId(), privateMessageVO.getContent());
    }

    private IMPrivateMessageTxEvent getEventMessage(String msg) {
        JSONObject jsonObject = JSONObject.parseObject(msg);
        String eventStr = jsonObject.getString(IMConstants.MSG_KEY);
        return JSONObject.parseObject(eventStr, IMPrivateMessageTxEvent.class);
    }
}
