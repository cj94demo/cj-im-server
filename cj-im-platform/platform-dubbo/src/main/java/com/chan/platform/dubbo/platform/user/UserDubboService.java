package com.chan.platform.dubbo.platform.user;

import com.chan.platform.common.model.entity.User;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/24 10:46
 * FileName: UserDubboService
 * Description: 用户的Dubbo服务
 */
public interface UserDubboService {
    /**
     * 根据用户id获取用户实体对象
     *
     * @param userId 用户id
     * @return 用户对象
     */
    User getUserById(Long userId);
}
