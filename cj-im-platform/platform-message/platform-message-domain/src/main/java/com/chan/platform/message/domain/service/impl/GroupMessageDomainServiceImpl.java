package com.chan.platform.message.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chan.platform.common.model.entity.GroupMessage;
import com.chan.platform.message.domain.repository.GroupMessageRepository;
import com.chan.platform.message.domain.service.GroupMessageDomainService;
import org.springframework.stereotype.Service;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/29
 * FileName: GroupMessageDomainServiceImpl
 * Description:
 */
@Service
public class GroupMessageDomainServiceImpl extends ServiceImpl<GroupMessageRepository, GroupMessage> implements GroupMessageDomainService {
}
