package com.chan.sdk.application.consumer;

import cn.hutool.core.util.StrUtil;
import com.chan.sdk.infrastructure.multicaster.MessageListenerMultiCaster;
import com.chan.serverdomain.constant.IMConstants;
import com.chan.serverdomain.enums.IMListenerType;
import com.chan.serverdomain.model.IMSendResult;
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
 * Date: 2024/6/17 19:48
 * FileName: PrivateMessageResultConsumer
 * Description:
 */
@Component
@ConditionalOnProperty(name = "message.mq.type", havingValue = "rocketmq")
@RocketMQMessageListener(consumerGroup = IMConstants.IM_RESULT_PRIVATE_CONSUMER_GROUP, topic = IMConstants.IM_RESULT_PRIVATE_QUEUE)
public class PrivateMessageResultConsumer extends BaseMessageResultConsumer implements RocketMQListener<String> {

    private final Logger logger = LoggerFactory.getLogger(PrivateMessageResultConsumer.class);

    @Autowired
    private MessageListenerMultiCaster messageListenerMultiCaster;

    @Override
    public void onMessage(String msg) {
        if (StrUtil.isBlank(msg)) {
            logger.warn("PrivateMessageResultConsumer.onMessage 接收的消息为空");
            return;
        }
        IMSendResult<?> resultMessage = this.getResultMessage(msg);
        if (resultMessage == null) {
            logger.warn("PrivateMessageResultConsumer.onMessage 转化后的数据为空");
            return;
        }
        messageListenerMultiCaster.multicast(IMListenerType.PRIVATE_MESSAGE, resultMessage);
    }
}
