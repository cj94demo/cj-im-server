package com.chan.platform.group.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chan.platform.common.model.entity.GroupMember;
import com.chan.platform.common.model.params.GroupParams;
import com.chan.platform.common.model.vo.GroupMemberSimpleVO;
import com.chan.platform.common.model.vo.GroupMemberVO;

import java.util.List;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/28 17:23
 * FileName: GroupMemberDomainService
 * Description: 群成员领域服务接口
 */
public interface GroupMemberDomainService extends IService<GroupMember> {
    /**
     * 保存群成员
     */
    boolean saveGroupMember(GroupMember groupMember);

    /**
     * 批量保存群成员
     */
    boolean saveGroupMemberList(List<GroupMember> groupMemberList);

    /**
     * 更新群成员数据
     */
    boolean updateGroupMember(GroupMember groupMember);

    /**
     * 移除群成员
     */
    boolean removeMemberByGroupId(Long groupId);

    /**
     * 移除指定成员
     */
    boolean removeMember(Long userId, Long groupId);

    /**
     * 获取群成员
     */
    GroupMember getGroupMemberByUserIdAndGroupId(Long userId, Long groupId);

    /**
     * 根据分组id获取成员列表
     */
    List<GroupMember> getGroupMemberListByGroupId(Long groupId);

    /**
     * 获取群组成员
     */
    List<GroupMemberVO> getGroupMemberVoListByGroupId(Long groupId);

    /**
     * 获取群成员id列表
     */
    List<Long> getUserIdsByGroupId(Long groupId);

    /**
     * 根据用户id获取群组id列表
     */
    List<Long> getGroupIdsByUserId(Long userId);

    /**
     * 获取成员
     */
    GroupMemberSimpleVO getGroupMemberSimpleVO(GroupParams groupParams);

    /**
     * 根据用户id获取在各个群组中的信息
     */
    List<GroupMemberSimpleVO> getGroupMemberSimpleVOList(Long userId);

    /**
     * 更新某个用户在所有群里的头像
     */
    boolean updateHeadImgByUserId(String headImg, Long userId);
}
