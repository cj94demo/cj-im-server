package com.chan.platform.message.application.tx;

import cn.hutool.core.util.BooleanUtil;
import com.alibaba.fastjson2.JSONObject;
import com.chan.platform.message.application.service.PrivateMessageService;
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
 * Date: 2024/6/29
 * FileName: IMPrivateMessageTxListener
 * Description:
 */
@Component
@RocketMQTransactionListener(/*txProducerGroup = SeckillConstants.TX_ORDER_PRODUCER_GROUP,*/ rocketMQTemplateBeanName = "rocketMQTemplate")
public class IMPrivateMessageTxListener implements RocketMQLocalTransactionListener {
    private final Logger logger = LoggerFactory.getLogger(IMPrivateMessageTxListener.class);
    @Autowired
    private PrivateMessageService privateMessageService;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            IMPrivateMessageTxEvent imPrivateMessageTxEvent = this.getTxMessage(message);
            boolean result = privateMessageService.saveIMPrivateMessageSaveEvent(imPrivateMessageTxEvent);
            if (result) {
                logger.info("executeLocalTransaction|消息微服务提交单聊本地事务成功|{}", imPrivateMessageTxEvent.getId());
                return RocketMQLocalTransactionState.COMMIT;
            }
            logger.info("executeLocalTransaction|消息微服务提交单聊本地事务失败|{}", imPrivateMessageTxEvent.getId());
            return RocketMQLocalTransactionState.ROLLBACK;
        } catch (Exception e) {
            logger.info("executeLocalTransaction|消息微服务提交单聊本地事务异常|{}", e.getMessage(), e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        IMPrivateMessageTxEvent imPrivateMessageTxEvent = this.getTxMessage(message);
        logger.info("checkLocalTransaction|消息微服务查询单聊消息本地事务|{}", imPrivateMessageTxEvent.getId());
        Boolean submitTransaction = privateMessageService.checkExists(imPrivateMessageTxEvent.getId());
        return BooleanUtil.isTrue(submitTransaction) ? RocketMQLocalTransactionState.COMMIT : RocketMQLocalTransactionState.UNKNOWN;
    }


    private IMPrivateMessageTxEvent getTxMessage(Message msg) {
        String messageString = new String((byte[]) msg.getPayload());
        JSONObject jsonObject = JSONObject.parseObject(messageString);
        String txStr = jsonObject.getString(IMConstants.MSG_KEY);
        return JSONObject.parseObject(txStr, IMPrivateMessageTxEvent.class);
    }
}
