package com.chan.platform.message.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chan.platform.common.model.entity.GroupMessage;
import com.chan.platform.common.model.vo.GroupMessageVO;
import com.chan.platform.message.domain.event.IMGroupMessageTxEvent;

import java.util.Date;
import java.util.List;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/29
 * FileName: GroupMessageDomainService
 * Description: 群聊消息
 */
public interface GroupMessageDomainService extends IService<GroupMessage> {
    /**
     * 保存群聊消息
     */
    boolean saveIMGroupMessageTxEvent(IMGroupMessageTxEvent imGroupMessageTxEvent);

    /**
     * 检测某条消息是否存在
     */
    boolean checkExists(Long messageId);

    /**
     * 拉取未读消息
     */
    List<GroupMessageVO> getUnreadGroupMessageList(Long groupId, Date sendTime, Long sendId, Integer status, Long maxReadId, Integer limitCount);

    /**
     * 拉取全站消息
     */
    List<GroupMessageVO> loadGroupMessageList(Long minId, Date minDate, List<Long> ids, Integer status, Integer limitCount);

    /**
     * 拉取在某个群的消息
     */
    List<GroupMessageVO> getHistoryMessage(Long groupId, Date sendTime, Integer status, long stIdx, long size);

    /**
     * 获取最大消息id
     */
    Long getMaxMessageId(Long groupId);
}
