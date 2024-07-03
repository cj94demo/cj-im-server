package com.chan.platform.message.application.listener;

import com.chan.platform.common.model.vo.GroupMessageVO;
import com.chan.sdk.domain.annotation.IMListener;
import com.chan.sdk.domain.listener.MessageListener;
import com.chan.servercache.distribute.DistributedCacheService;
import com.chan.serverdomain.constant.IMConstants;
import com.chan.serverdomain.enums.IMListenerType;
import com.chan.serverdomain.enums.IMSendCode;
import com.chan.serverdomain.model.IMSendResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/7/3 16:38
 * FileName: GroupMessageListener
 * Description: 监听群消息
 */
@IMListener(listenerType = IMListenerType.GROUP_MESSAGE)
public class GroupMessageListener implements MessageListener<GroupMessageVO> {
    @Autowired
    private DistributedCacheService distributedCacheService;

    @Override
    public void doProcess(IMSendResult<GroupMessageVO> result) {
        GroupMessageVO messageInfo = result.getData();
        if (IMSendCode.SUCCESS.getCode().equals(result.getCode())) {
            String redisKey = String.join(IMConstants.REDIS_KEY_SPLIT, IMConstants.IM_GROUP_READED_POSITION, messageInfo.getGroupId().toString(), result.getReceiver().getUserId().toString());
            distributedCacheService.set(redisKey, messageInfo.getId());
        }
    }

}
