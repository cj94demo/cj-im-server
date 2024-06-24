package com.chan.platform.friend.application.consumer;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.chan.platform.common.model.contants.IMPlatformConstants;
import com.chan.platform.common.model.event.User2FriendEvent;
import com.chan.platform.friend.domain.service.FriendDomainService;
import com.chan.serverdomain.constant.IMConstants;
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
 * Date: 2024/6/24 21:03
 * FileName: IMFriendRocketMQEventConsumer
 * Description: 消费消息
 */
@Component
@ConditionalOnProperty(name = "message.mq.type", havingValue = "rocketmq")
@RocketMQMessageListener(consumerGroup = IMPlatformConstants.TOPIC_USER_TO_FRIEND_GROUP, topic = IMPlatformConstants.TOPIC_USER_TO_FRIEND)
public class IMFriendRocketMQEventConsumer implements RocketMQListener<String> {
    private final Logger logger = LoggerFactory.getLogger(IMFriendRocketMQEventConsumer.class);
    @Autowired
    private FriendDomainService domainService;

    @Override
    public void onMessage(String message) {
        if (StrUtil.isEmpty(message)) {
            logger.info("rocketmq|friendConsumer|接收用户微服务发送过来的事件参数为空");
            return;
        }
        logger.info("rocketmq|friendConsumer|接收用户微服务发送过来的事件|{}", message);
        User2FriendEvent user2FriendEvent = this.getEventMessage(message);
        domainService.updateFriendByFriendId(user2FriendEvent.getHeadImg(), user2FriendEvent.getNickName(), user2FriendEvent.getId());
    }

    private User2FriendEvent getEventMessage(String msg) {
        JSONObject jsonObject = JSONObject.parseObject(msg);
        String eventStr = jsonObject.getString(IMConstants.MSG_KEY);
        return JSONObject.parseObject(eventStr, User2FriendEvent.class);
    }
}
