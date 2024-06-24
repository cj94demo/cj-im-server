package com.chan.platform.friend.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chan.platform.common.model.entity.Friend;
import com.chan.platform.common.model.vo.FriendVO;
import com.chan.platform.friend.domain.model.command.FriendCommand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/24 21:10
 * FileName: FriendDomainService
 * Description: 好友领域服务
 */
public interface FriendDomainService extends IService<Friend> {
    /**
     * 根据用户id获取好友的id列表
     */
    List<Long> getFriendIdList(Long userId);

    /**
     * 根据用户id获取好友列表
     */
    List<FriendVO> findFriendByUserId(Long userId);

    /**
     * 判断两个用户是否是好友关系
     */
    Boolean isFriend(Long userId1, Long userId2);

    /**
     * 绑定好友关系
     */
    void bindFriend(FriendCommand friendCommand, String headImg, String nickName);

    /**
     * 解除好友关系
     */
    void unbindFriend(FriendCommand friendCommand);

    /**
     * 更新好友数据
     */
    void update(FriendVO vo, Long userId);

    /**
     * 获取好友信息
     */
    FriendVO findFriend(FriendCommand friendCommand);

    /**
     * 根据用户id获取好友列表
     */
    List<Friend> getFriendByUserId(Long userId);

    /**
     * 更新好友数据表数据
     */
    int updateFriendByFriendId(@Param("headImage") String headImage, String nickName, Long friendId);
}
