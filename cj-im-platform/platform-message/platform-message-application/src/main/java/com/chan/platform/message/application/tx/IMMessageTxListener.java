package com.chan.platform.message.application.tx;

import cn.hutool.core.util.BooleanUtil;
import com.alibaba.fastjson2.JSONObject;
import com.chan.platform.common.model.contants.IMPlatformConstants;
import com.chan.platform.message.application.service.GroupMessageService;
import com.chan.platform.message.application.service.PrivateMessageService;
import com.chan.platform.message.domain.event.IMGroupMessageTxEvent;
import com.chan.platform.message.domain.event.IMMessageTxEvent;
import com.chan.platform.message.domain.event.IMPrivateMessageTxEvent;
import com.chan.serverdomain.constant.IMConstants;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/7/2 19:52
 * FileName: IMMessageTxListener
 * Description: 监听事务消息
 */
@Component
@RocketMQTransactionListener(rocketMQTemplateBeanName = "rocketMQTemplate")
public class IMMessageTxListener implements RocketMQLocalTransactionListener {
    private final Logger logger = LoggerFactory.getLogger(IMMessageTxListener.class);
    @Autowired
    private PrivateMessageService privateMessageService;
    @Autowired
    private GroupMessageService groupMessageService;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            IMMessageTxEvent imMessageTxEvent = this.getTxMessage(message);
            switch (imMessageTxEvent.getMessageType()) {
                //单聊
                case IMPlatformConstants.TYPE_MESSAGE_PRIVATE:
                    return executePrivateMessageLocalTransaction(message);
                //群聊
                case IMPlatformConstants.TYPE_MESSAGE_GROUP:
                    return executeGroupMessageLocalTransaction(message);
                default:
                    return executePrivateMessageLocalTransaction(message);
            }
        } catch (Exception e) {
            logger.info("executeLocalTransaction|消息微服务提交本地事务异常|{}", e.getMessage(), e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    private RocketMQLocalTransactionState executeGroupMessageLocalTransaction(Message message) {
        IMGroupMessageTxEvent imGroupMessageTxEvent = this.getTxGroupMessage(message);
        boolean result = groupMessageService.saveIMGroupMessageTxEvent(imGroupMessageTxEvent);
        if (result) {
            logger.info("executeGroupMessageLocalTransaction|消息微服务提交群聊本地事务成功|{}", imGroupMessageTxEvent.getId());
            return RocketMQLocalTransactionState.COMMIT;
        }
        logger.info("executeGroupMessageLocalTransaction|消息微服务提交群聊本地事务失败|{}", imGroupMessageTxEvent.getId());
        return RocketMQLocalTransactionState.ROLLBACK;
    }

    private RocketMQLocalTransactionState executePrivateMessageLocalTransaction(Message message) {
        IMPrivateMessageTxEvent imPrivateMessageTxEvent = this.getTxPrivateMessage(message);
        boolean result = privateMessageService.saveIMPrivateMessageSaveEvent(imPrivateMessageTxEvent);
        if (result) {
            logger.info("executePrivateMessageLocalTransaction|消息微服务提交单聊本地事务成功|{}", imPrivateMessageTxEvent.getId());
            return RocketMQLocalTransactionState.COMMIT;
        }
        logger.info("executePrivateMessageLocalTransaction|消息微服务提交单聊本地事务失败|{}", imPrivateMessageTxEvent.getId());
        return RocketMQLocalTransactionState.ROLLBACK;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        IMMessageTxEvent imMessageTxEvent = this.getTxMessage(message);
        logger.info("checkLocalTransaction|消息微服务查询本地事务|{}", imMessageTxEvent.getId());
        boolean submitTransaction = switch (imMessageTxEvent.getMessageType()) {
            //单聊
            case IMPlatformConstants.TYPE_MESSAGE_PRIVATE ->
                    privateMessageService.checkExists(imMessageTxEvent.getId());
            //群聊
            case IMPlatformConstants.TYPE_MESSAGE_GROUP -> groupMessageService.checkExists(imMessageTxEvent.getId());
            default -> privateMessageService.checkExists(imMessageTxEvent.getId());
        };
        return BooleanUtil.isTrue(submitTransaction) ? RocketMQLocalTransactionState.COMMIT : RocketMQLocalTransactionState.UNKNOWN;
    }

    private IMMessageTxEvent getTxMessage(Message msg) {
        String messageString = new String((byte[]) msg.getPayload());
        JSONObject jsonObject = JSONObject.parseObject(messageString);
        String txStr = jsonObject.getString(IMConstants.MSG_KEY);
        return JSONObject.parseObject(txStr, IMMessageTxEvent.class);
    }

    private IMPrivateMessageTxEvent getTxPrivateMessage(Message msg) {
        String messageString = new String((byte[]) msg.getPayload());
        JSONObject jsonObject = JSONObject.parseObject(messageString);
        String txStr = jsonObject.getString(IMConstants.MSG_KEY);
        return JSONObject.parseObject(txStr, IMPrivateMessageTxEvent.class);
    }

    private IMGroupMessageTxEvent getTxGroupMessage(Message msg) {
        String messageString = new String((byte[]) msg.getPayload());
        JSONObject jsonObject = JSONObject.parseObject(messageString);
        String txStr = jsonObject.getString(IMConstants.MSG_KEY);
        return JSONObject.parseObject(txStr, IMGroupMessageTxEvent.class);
    }
}
