package com.chan.platform.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Date;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/17 21:44
 * FileName: Friend
 * Description: 好友表
 */
@TableName("im_friend")
public class Friend extends Model<Friend> {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户id
     */
    @TableField("user_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 好友id
     */
    @TableField("friend_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long friendId;

    /**
     * 用户昵称
     */
    @TableField("friend_nick_name")
    private String friendNickName;

    /**
     * 用户头像
     */
    @TableField("friend_head_image")
    private String friendHeadImage;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public String getFriendNickName() {
        return friendNickName;
    }

    public void setFriendNickName(String friendNickName) {
        this.friendNickName = friendNickName;
    }

    public String getFriendHeadImage() {
        return friendHeadImage;
    }

    public void setFriendHeadImage(String friendHeadImage) {
        this.friendHeadImage = friendHeadImage;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
