package com.chan.platform.message.domain.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chan.platform.common.model.entity.PrivateMessage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/29
 * FileName: PrivateMessageRepository
 * Description: 单聊消息数仓
 */
public interface PrivateMessageRepository extends BaseMapper<PrivateMessage> {
    @Select("select 1 from im_private_message where id = #{messageId} limit 1")
    Integer checkExists(@Param("messageId") Long messageId);
}
