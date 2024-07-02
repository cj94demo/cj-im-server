package com.chan.platform.common.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/18 10:53
 * FileName: GroupMemberVO
 * Description: 群成员
 */
@ApiModel("群成员信息VO")
public class GroupMemberVO extends GroupMemberSimpleVO {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("头像")
    private String headImage;

    @ApiModelProperty(value = "是否在线")
    private Boolean online;

    @ApiModelProperty("备注")
    private String remark;

    public GroupMemberVO() {
    }

    public GroupMemberVO(Long userId, String aliasName, String headImage, Boolean quit, Boolean online, String remark) {
        super(aliasName, quit);
        this.userId = userId;
        this.headImage = headImage;
        this.online = online;
        this.remark = remark;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
