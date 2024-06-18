package com.chan.platform.common.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/18 10:33
 * FileName: GroupInviteVO
 * Description: 邀请进群
 */
@ApiModel("邀请好友进群请求VO")
public class GroupInviteVO {

    @NotNull(message = "群id不可为空")
    @ApiModelProperty(value = "群id")
    private Long groupId;

    @NotEmpty(message = "群id不可为空")
    @ApiModelProperty(value = "好友id列表不可为空")
    private List<Long> friendIds;

    public GroupInviteVO() {
    }

    public GroupInviteVO(Long groupId, List<Long> friendIds) {
        this.groupId = groupId;
        this.friendIds = friendIds;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public List<Long> getFriendIds() {
        return friendIds;
    }

    public void setFriendIds(List<Long> friendIds) {
        this.friendIds = friendIds;
    }
}
