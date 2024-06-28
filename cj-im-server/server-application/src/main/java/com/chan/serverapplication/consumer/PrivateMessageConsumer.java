package com.chan.serverapplication.consumer;

import cn.hutool.core.util.StrUtil;
import com.chan.serverapplication.netty.processor.MessageProcessor;
import com.chan.serverapplication.netty.processor.factory.ProcessorFactory;
import com.chan.serverdomain.constant.IMConstants;
import com.chan.serverdomain.enums.IMCmdType;
import com.chan.serverdomain.model.IMReceiveInfo;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/13 21:44
 * FileName: PrivateMessageConsumer
 * Description: 消息消费者
 */
@Component
@ConditionalOnProperty(name = "message.mq.type", havingValue = "rocketmq")
@RocketMQMessageListener(consumerGroup = IMConstants.IM_MESSAGE_PRIVATE_CONSUMER_GROUP, topic = IMConstants.IM_MESSAGE_PRIVATE_NULL_QUEUE)
public class PrivateMessageConsumer extends BaseMessageConsumer implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {
    private final Logger logger = LoggerFactory.getLogger(PrivateMessageConsumer.class);

    @Value("${server.id}")
    private Long serverId;

    @Override
    public void onMessage(String message) {
        if (StrUtil.isEmpty(message)) {
            logger.warn("PrivateMessageConsumer.onMessage|接收到的消息为空");
            return;
        }
        IMReceiveInfo imReceiveInfo = this.getReceiveMessage(message);
        if (imReceiveInfo == null) {
            logger.warn("PrivateMessageConsumer.onMessage|转化后的数据为空");
            return;
        }
        MessageProcessor processor = ProcessorFactory.getProcessor(IMCmdType.PRIVATE_MESSAGE);
        processor.process(imReceiveInfo);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        try {
            String topic = String.join(IMConstants.MESSAGE_KEY_SPLIT, IMConstants.IM_MESSAGE_PRIVATE_QUEUE, String.valueOf(serverId));
            consumer.subscribe(topic, "*");
        } catch (Exception e) {
            logger.error("PrivateMessageConsumer.prepareStart|异常:{}", e.getMessage());
        }
    }

}
