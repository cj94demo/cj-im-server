package com.chan.platform.group.domain.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chan.platform.common.model.entity.GroupMember;
import com.chan.platform.common.model.vo.GroupMemberSimpleVO;
import com.chan.platform.common.model.vo.GroupMemberVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/28 17:20
 * FileName: GroupMemberRepository
 * Description: 群成员数仓
 */
public interface GroupMemberRepository extends BaseMapper<GroupMember> {
    @Select("select user_id as userId, alias_name as aliasName, head_image as headImage, quit as quit, " +
            "remark as remark, created_time as createdTime, group_id as groupId from im_group_member where group_id = #{groupId} and quit = 0")
    List<GroupMemberVO> getGroupMemberVoListByGroupId(@Param("groupId") Long groupId);

    @Select("select user_id from im_group_member where group_id = #{groupId} and quit = 0 ")
    List<Long> getUserIdsByGroupId(@Param("groupId") Long groupId);

    @Select("select group_id from im_group_member where user_id = #{userId} and quit = 0 ")
    List<Long> getGroupIdsByUserId(@Param("userId") Long userId);

    @Select("select alias_name as aliasName, quit as quit, created_time as createdTime, group_id as groupId from im_group_member where group_id = #{groupId} and user_id = #{userId} and quit = 0 ")
    GroupMemberSimpleVO getGroupMemberSimpleVO(@Param("groupId") Long groupId, @Param("userId") Long userId);

    @Select("select alias_name as aliasName, quit as quit, created_time as createdTime, group_id as groupId from im_group_member where user_id = #{userId} and quit = 0 ")
    List<GroupMemberSimpleVO> getGroupMemberSimpleVOList(@Param("userId") Long userId);

    @Update("update im_group_member set head_image = #{headImg} where user_id = #{userId}")
    int updateHeadImgByUserId(@Param("headImg") String headImg, @Param("userId") Long userId);
}
