package com.chan.platform.user.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.chan.platform.common.exception.IMException;
import com.chan.platform.common.jwt.JwtProperties;
import com.chan.platform.common.model.contants.IMPlatformConstants;
import com.chan.platform.common.model.dto.LoginDTO;
import com.chan.platform.common.model.dto.RegisterDTO;
import com.chan.platform.common.model.entity.User;
import com.chan.platform.common.model.enums.HttpCode;
import com.chan.platform.common.model.vo.LoginVO;
import com.chan.platform.common.session.UserSession;
import com.chan.platform.common.utils.BeanUtils;
import com.chan.platform.user.application.service.UserService;
import com.chan.platform.user.domain.service.UserDomainService;
import com.chan.servercache.distribute.DistributedCacheService;
import com.chan.servercache.id.SnowFlakeFactory;
import com.chan.serverdomain.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/18 19:36
 * FileName: UserServiceImpl
 * Description: 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDomainService userDomainService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DistributedCacheService distributedCacheService;
    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public LoginVO login(LoginDTO dto) {
        if (dto == null) {
            throw new IMException(HttpCode.PARAMS_ERROR);
        }
        User user = distributedCacheService.queryWithPassThrough(IMPlatformConstants.PLATFORM_REDIS_USER_KEY, dto.getUserName(),
                User.class, userDomainService::getUserByUserName, IMPlatformConstants.DEFAULT_REDIS_CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        if (user == null) {
            throw new IMException(HttpCode.PROGRAM_ERROR, "当前用户不存在");
        }
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IMException(HttpCode.PASSWOR_ERROR);
        }
        //生成token
        UserSession session = BeanUtils.copyProperties(user, UserSession.class);
        session.setUserId(user.getId());
        session.setTerminal(dto.getTerminal());
        String strJson = JSON.toJSONString(session);
        String accessToken = JwtUtils.sign(user.getId(), strJson, jwtProperties.getAccessTokenExpireIn(), jwtProperties.getAccessTokenSecret());
        String refreshToken = JwtUtils.sign(user.getId(), strJson, jwtProperties.getRefreshTokenExpireIn(), jwtProperties.getRefreshTokenSecret());
        LoginVO vo = new LoginVO();
        vo.setAccessToken(accessToken);
        vo.setAccessTokenExpiresIn(jwtProperties.getAccessTokenExpireIn());
        vo.setRefreshToken(refreshToken);
        vo.setRefreshTokenExpiresIn(jwtProperties.getRefreshTokenExpireIn());
        return vo;
    }

    @Override
    public void register(RegisterDTO dto) {
        if (dto == null) {
            throw new IMException(HttpCode.PARAMS_ERROR);
        }
        User user = distributedCacheService.queryWithPassThrough(IMPlatformConstants.PLATFORM_REDIS_USER_KEY, dto.getUserName(),
                User.class, userDomainService::getUserByUserName, IMPlatformConstants.DEFAULT_REDIS_CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        if (user != null) {
            throw new IMException(HttpCode.USERNAME_ALREADY_REGISTER);
        }
        user = BeanUtils.copyProperties(dto, User.class);
        user.setId(SnowFlakeFactory.getSnowFlakeFromCache().nextId());
        user.setCreatedTime(new Date());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDomainService.saveOrUpdateUser(user);
        logger.info("注册用户，用户id:{},用户名:{},昵称:{}", user.getId(), dto.getUserName(), dto.getNickName());
    }
}
