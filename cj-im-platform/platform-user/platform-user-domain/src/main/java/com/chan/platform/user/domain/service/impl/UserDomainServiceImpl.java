package com.chan.platform.user.domain.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chan.platform.common.exception.IMException;
import com.chan.platform.common.model.entity.User;
import com.chan.platform.common.model.enums.HttpCode;
import com.chan.platform.user.domain.repository.UserRepository;
import com.chan.platform.user.domain.service.UserDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/18 19:29
 * FileName: UserDomainServiceImpl
 * Description: 领域层用户服务实现类
 */
@Service
public class UserDomainServiceImpl extends ServiceImpl<UserRepository, User> implements UserDomainService {
    @Override
    public User getUserByUserName(String userName) {
        if (StrUtil.isBlank(userName)) {
            throw new IMException(HttpCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, userName);
        return this.getOne(wrapper);
    }

    @Override
    public void saveOrUpdateUser(User user) {
        if (user == null) {
            throw new IMException(HttpCode.PARAMS_ERROR);
        }
        this.saveOrUpdate(user);
    }

    @Override
    public List<User> getUserListByName(String name) {
        if (StrUtil.isEmpty(name)) {
            throw new IMException(HttpCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(User::getUserName, name).or().like(User::getNickName, name).last("limit 20");
        return this.list(queryWrapper);
    }
}
