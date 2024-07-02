package com.chan.platform.message.domain.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chan.platform.common.model.entity.PrivateMessage;
import com.chan.platform.common.model.vo.PrivateMessageVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

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

    @Select({"<script> " +
            "select id as id, send_id as sendId, recv_id as recvId, content as content, type as type, status as status, send_time as sendTime " +
            "from im_private_message where recv_id = #{userId} and status = 0 and send_id in   " +
            "<foreach collection='friendIds' item='friendId' index='index' separator=',' open='(' close=')'> " +
            " #{friendId} " +
            " </foreach> " +
            "</script>"})
    List<PrivateMessageVO> getPrivateMessageVOList(@Param("userId") Long userId, @Param("friendIds") List<Long> friendIds);

    @Select({"<script> " +
            "select id as id, send_id as sendId, recv_id as recvId, content as content, type as type, status as status, send_time as sendTime " +
            "from im_private_message where id  <![CDATA[ > ]]> #{minId} and send_time  <![CDATA[ >= ]]> #{minDate} and status  <![CDATA[ <> ]]> 2 and ( " +
            " ( " +
            "send_id = #{userId} and recv_id in " +
            "<foreach collection='friendIds' item='friendId' index='index' separator=',' open='(' close=')'> " +
            " #{friendId} " +
            " </foreach> " +
            " ) " +
            " or " +
            " (" +
            "recv_id = #{userId} and send_id in " +
            "<foreach collection='friendIds' item='friendId' separator=',' open='(' close=')'> " +
            " #{friendId} " +
            " </foreach> " +
            ") " +
            " ) order by id asc limit #{limitCount} " +
            "</script>"})
    List<PrivateMessageVO> loadMessage(@Param("userId") Long userId, @Param("minId") Long minId, @Param("minDate") Date minDate, @Param("friendIds") List<Long> friendIds, @Param("limitCount") int limitCount);

    @Update({"<script> " +
            "update im_private_message set status = #{status} where id in " +
            " <foreach collection='ids' item='id' index='index' separator=',' open='(' close=')'>  " +
            " #{id} " +
            " </foreach> " +
            "</script>"})
    int batchUpdatePrivateMessageStatus(@Param("status") Integer status, @Param("ids") List<Long> ids);

    @Select({"<script> " +
            "select id as id, send_id as sendId, recv_id as recvId, content as content, type as type, status as status, send_time as sendTime " +
            "from im_private_message where " +
            " ((send_id = #{userId} and recv_id = #{friendId}) or (send_id = #{friendId} and recv_id = #{userId})) " +
            "and status  <![CDATA[ <> ]]> 2 order by id desc limit #{stIdx}, #{size} " +
            "</script>"})
    List<PrivateMessageVO> loadMessageByUserIdAndFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId, @Param("stIdx") long stIdx, @Param("size") long size);

    @Update("update im_private_message set status = 3 where send_id = #{sendId} and recv_id = #{recvId} and status = 1 ")
    int readedMessage(@Param("sendId") Long sendId, @Param("recvId") Long recvId);
}
