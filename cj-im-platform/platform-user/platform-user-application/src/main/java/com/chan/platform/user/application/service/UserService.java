package com.chan.platform.user.application.service;

import com.chan.platform.common.model.dto.LoginDTO;
import com.chan.platform.common.model.dto.RegisterDTO;
import com.chan.platform.common.model.vo.LoginVO;

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
}
