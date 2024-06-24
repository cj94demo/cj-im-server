package com.chan.platform.user.application.service;

import com.chan.platform.common.model.dto.LoginDTO;
import com.chan.platform.common.model.dto.ModifyPwdDTO;
import com.chan.platform.common.model.dto.RegisterDTO;
import com.chan.platform.common.model.entity.User;
import com.chan.platform.common.model.vo.LoginVO;
import com.chan.platform.common.model.vo.OnlineTerminalVO;
import com.chan.platform.common.model.vo.UserVO;

import java.util.List;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/18 19:36
 * FileName: UserService
 * Description: 用户服务
 */
public interface UserService {
    /**
     * 用户登录
     */
    LoginVO login(LoginDTO dto);

    /**
     * 用户注册
     */
    void register(RegisterDTO dto);

    /**
     * 用refreshToken换取新 token
     */
    LoginVO refreshToken(String refreshToken);

    /**
     * 修改用户密码
     */
    void modifyPassword(ModifyPwdDTO dto);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    User findUserByUserName(String username);

    /**
     * 更新用户信息，好友昵称和群聊昵称等冗余信息也会更新
     *
     * @param vo 用户信息vo
     */
    void update(UserVO vo);

    /**
     * 根据用户昵id查询用户以及在线状态
     *
     * @param id 用户id
     * @return 用户信息
     */
    UserVO findUserById(Long id, boolean constantsOnlineFlag);

    /**
     * 根据用户id获取用户实体对象
     *
     * @param userId 用户id
     * @return 用户对象
     */
    User getUserById(Long userId);

    /**
     * 根据用户昵称查询用户，最多返回20条数据
     *
     * @param name 用户名或昵称
     * @return 用户列表
     */
    List<UserVO> findUserByName(String name);

    /**
     * 获取用户在线的终端类型
     *
     * @param userIds 用户id，多个用‘,’分割
     * @return 在线用户终端
     */
    List<OnlineTerminalVO> getOnlineTerminals(String userIds);
}
