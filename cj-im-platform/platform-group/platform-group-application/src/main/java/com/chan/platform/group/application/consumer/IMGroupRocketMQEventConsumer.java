package com.chan.platform.group.application.consumer;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.chan.platform.common.model.contants.IMPlatformConstants;
import com.chan.platform.common.model.event.User2GroupEvent;
import com.chan.platform.group.application.service.GroupService;
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
 * Date: 2024/6/28 17:38
 * FileName: IMGroupRocketMQEventConsumer
 * Description: 消费消息
 */
@Component
@ConditionalOnProperty(name = "message.mq.type", havingValue = "rocketmq")
@RocketMQMessageListener(consumerGroup = IMPlatformConstants.TOPIC_USER_TO_GROUP_GROUP, topic = IMPlatformConstants.TOPIC_USER_TO_GROUP)
public class IMGroupRocketMQEventConsumer implements RocketMQListener<String> {
    private final Logger logger = LoggerFactory.getLogger(IMGroupRocketMQEventConsumer.class);
    @Autowired
    private GroupService groupService;

    @Override
    public void onMessage(String message) {
        if (StrUtil.isEmpty(message)) {
            logger.info("rocketmq|groupConsumer|接收群组微服务发送过来的事件参数为空");
            return;
        }
        logger.info("rocketmq|groupConsumer|接收群组微服务发送过来的事件|{}", message);
        User2GroupEvent user2GroupEvent = this.getEventMessage(message);
        groupService.updateHeadImgByUserId(user2GroupEvent.getHeadImageThumb(), user2GroupEvent.getId());
    }

    private User2GroupEvent getEventMessage(String msg) {
        JSONObject jsonObject = JSONObject.parseObject(msg);
        String eventStr = jsonObject.getString(IMConstants.MSG_KEY);
        return JSONObject.parseObject(eventStr, User2GroupEvent.class);
    }
}
