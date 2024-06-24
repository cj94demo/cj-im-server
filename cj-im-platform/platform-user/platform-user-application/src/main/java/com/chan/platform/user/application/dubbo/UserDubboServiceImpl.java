package com.chan.platform.user.application.dubbo;

import com.chan.platform.common.model.contants.IMPlatformConstants;
import com.chan.platform.common.model.entity.User;
import com.chan.platform.dubbo.platform.user.UserDubboService;
import com.chan.platform.user.application.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/24 10:26
 * FileName: UserDubboServiceImpl
 * Description: 用户的Dubbo服务实现
 */
@Component
@DubboService(version = IMPlatformConstants.DEFAULT_DUBBO_VERSION)
public class UserDubboServiceImpl implements UserDubboService {
    @Autowired
    private UserService userService;

    @Override
    public User getUserById(Long userId) {
        return userService.getUserById(userId);
    }
}
