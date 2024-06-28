package com.chan.platform.group.application.dubbo;

import com.chan.platform.common.model.contants.IMPlatformConstants;
import com.chan.platform.common.model.entity.Group;
import com.chan.platform.common.model.params.GroupParams;
import com.chan.platform.common.model.vo.GroupMemberSimpleVO;
import com.chan.platform.dubbo.platform.group.GroupDubboService;
import com.chan.platform.group.application.service.GroupService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/28 17:39
 * FileName: GroupDubboServiceImpl
 * Description: 群组Dubbo
 */
@Component
@DubboService(version = IMPlatformConstants.DEFAULT_DUBBO_VERSION)
public class GroupDubboServiceImpl implements GroupDubboService {
    @Autowired
    private GroupService groupService;

    @Override
    public boolean isExists(Long groupId) {
        Group group = groupService.getById(groupId);
        if (Objects.isNull(group)) {
            return false;
        }
        if (group.getDeleted()) {
            return false;
        }
        return true;
    }

    @Override
    public GroupMemberSimpleVO getGroupMemberSimpleVO(GroupParams groupParams) {
        return groupService.getGroupMemberSimpleVO(groupParams);
    }

    @Override
    public List<Long> getUserIdsByGroupId(Long groupId) {
        return groupService.getUserIdsByGroupId(groupId);
    }

    @Override
    public List<Long> getGroupIdsByUserId(Long userId) {
        return groupService.getGroupIdsByUserId(userId);
    }

    @Override
    public List<GroupMemberSimpleVO> getGroupMemberSimpleVOList(Long userId) {
        return groupService.getGroupMemberSimpleVOList(userId);
    }
}
