package com.chan.platform.common.risk.rule.service;

import com.chan.platform.common.model.enums.HttpCode;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/18 14:54
 * FileName: RuleChainService
 * Description: IM大后端平台的规则调用链接口
 */
public interface RuleChainService {
    /**
     * 执行处理逻辑
     */
    HttpCode execute(HttpServletRequest request, Object handler);

    /**
     * 规则链中的每个规则排序
     */
    int getOrder();

}
