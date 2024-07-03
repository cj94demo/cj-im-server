package com.chan.platform.message.application.service.impl;

import com.chan.platform.common.exception.IMException;
import com.chan.platform.common.model.contants.IMPlatformConstants;
import com.chan.platform.common.model.enums.MessageType;
import com.chan.platform.common.model.vo.PrivateMessageVO;
import com.chan.platform.common.session.SessionContext;
import com.chan.platform.common.session.UserSession;
import com.chan.platform.common.session.WebrtcSession;
import com.chan.platform.message.application.service.WebrtcService;
import com.chan.sdk.core.client.IMClient;
import com.chan.servercache.distribute.DistributedCacheService;
import com.chan.serverdomain.constant.IMConstants;
import com.chan.serverdomain.model.IMPrivateMessage;
import com.chan.serverdomain.model.IMUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/7/3 16:48
 * FileName: WebrtcServiceImpl
 * Description: 视频消息服务
 */
@Service
public class WebrtcServiceImpl implements WebrtcService {
    @Autowired
    private IMClient imClient;
    @Autowired
    private DistributedCacheService distributedCacheService;

    @Override
    public void call(Long uid, String offer) {
        UserSession session = SessionContext.getSession();
        if (!imClient.isOnline(uid)) {
            throw new IMException("对方目前不在线");
        }
        // 创建webrtc会话
        WebrtcSession webrtcSession = new WebrtcSession();
        webrtcSession.setCallerId(session.getUserId());
        webrtcSession.setCallerTerminal(session.getTerminal());
        String key = getSessionKey(session.getUserId(), uid);
        distributedCacheService.set(key, webrtcSession, IMPlatformConstants.WEBRTC_SESSION_CACHE_EXPIRE, TimeUnit.HOURS);
        // 向对方所有终端发起呼叫
        PrivateMessageVO messageInfo = new PrivateMessageVO();
        messageInfo.setType(MessageType.RTC_CALL.code());
        messageInfo.setRecvId(uid);
        messageInfo.setSendId(session.getUserId());
        messageInfo.setContent(offer);

        IMPrivateMessage<PrivateMessageVO> sendMessage = new IMPrivateMessage<>();
        sendMessage.setSender(new IMUserInfo(session.getUserId(), session.getTerminal()));
        sendMessage.setReceiveId(uid);
        sendMessage.setSendToSelf(false);
        sendMessage.setSendResult(false);
        sendMessage.setData(messageInfo);
        imClient.sendPrivateMessage(sendMessage);
    }

    private String getSessionKey(Long id1, Long id2) {
        Long minId = id1 > id2 ? id2 : id1;
        Long maxId = id1 > id2 ? id1 : id2;
        return String.join(IMConstants.REDIS_KEY_SPLIT, IMConstants.IM_WEBRTC_SESSION, minId.toString(), maxId.toString());
    }

}
