package com.chan.platform.message.application.listener;

import com.alibaba.fastjson2.JSON;
import com.chan.platform.common.model.vo.PrivateMessageVO;
import com.chan.sdk.domain.annotation.IMListener;
import com.chan.sdk.domain.listener.MessageListener;
import com.chan.serverdomain.enums.IMListenerType;
import com.chan.serverdomain.model.IMSendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/7/3 15:38
 * FileName: PrivateMessageListener
 * Description: 监听私聊消息
 */
@IMListener(listenerType = IMListenerType.PRIVATE_MESSAGE)
public class PrivateMessageListener implements MessageListener<PrivateMessageVO> {
    private final Logger logger = LoggerFactory.getLogger(PrivateMessageListener.class);

    @Override
    public void doProcess(IMSendResult<PrivateMessageVO> result) {
        logger.info("PrivateMessageListener|监听到单聊消息数据|{}", JSON.toJSONString(result));
    }

}
