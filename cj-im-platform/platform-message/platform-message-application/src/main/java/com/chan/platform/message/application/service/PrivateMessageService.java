package com.chan.platform.message.application.service;

import com.chan.platform.common.model.dto.PrivateMessageDTO;
import com.chan.platform.common.model.vo.PrivateMessageVO;
import com.chan.platform.message.domain.event.IMPrivateMessageTxEvent;

import java.util.List;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/29 14:19
 * FileName: PrivateMessageService
 * Description: 单聊消息
 */
public interface PrivateMessageService {
    /**
     * 发送私聊消息
     *
     * @param dto 私聊消息
     * @return 消息id
     */
    Long sendMessage(PrivateMessageDTO dto);

    /**
     * 保存单聊消息
     */
    boolean saveIMPrivateMessageSaveEvent(IMPrivateMessageTxEvent privateMessageSaveEvent);

    /**
     * 检测数据
     */
    boolean checkExists(Long messageId);

    /**
     * 异步拉取单聊未读消息
     */
    void pullUnreadMessage();

    /**
     * 拉取消息，只能拉取最近1个月的消息，一次拉取100条
     */
    List<PrivateMessageVO> loadMessage(Long minId);

    /**
     * 拉取历史聊天记录
     */
    List<PrivateMessageVO> getHistoryMessage(Long friendId, Long page, Long size);

    void readedMessage(Long friendId);
}
