package com.chan.platform.common.risk.rule.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.chan.platform.common.model.enums.HttpCode;
import com.chan.platform.common.risk.enums.RuleEnum;
import com.chan.platform.common.risk.rule.service.RuleChainService;
import com.chan.platform.common.risk.rule.service.base.BaseRuleChainService;
import com.chan.platform.common.risk.window.SlidingWindowLimitService;
import com.chan.platform.common.session.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/18 16:47
 * FileName: PathRuleChainService
 * Description: 访问资源限制规则
 */
@Component
public class PathRuleChainService extends BaseRuleChainService implements RuleChainService {
    private final Logger logger = LoggerFactory.getLogger(PathRuleChainService.class);

    @Value("${cj.im.rule.pathRule.enabled}")
    private Boolean pathRuleEnabled;

    @Value("${cj.im.rule.pathRule.order}")
    private Integer pathRuleOrder;
    /**
     * 滑动窗口大小
     */
    @Value("${cj.im.rule.pathRule.windowsSize}")
    private Integer windowsSize;
    /**
     * 限流窗口的周期
     */
    @Value("${cj.im.rule.pathRule.windowPeriod}")
    private Long windowPeriod;

    @Autowired
    private SlidingWindowLimitService slidingWindowLimitService;

    @Override
    public HttpCode execute(HttpServletRequest request, Object handler) {
        if (BooleanUtil.isFalse(pathRuleEnabled)) {
            return HttpCode.SUCCESS;
        }
        try {
            UserSession userSession = this.getUserSessionWithoutException(request);
            if (userSession == null) {
                return HttpCode.SUCCESS;
            }
            windowsSize = windowsSize == null ? DEFAULT_WINDOWS_SIZE : windowsSize;
            windowPeriod = windowPeriod == null ? DEFAULT_WINDOWS_PERIOD : windowPeriod;
            String userPath = request.getRequestURI().concat(String.valueOf(userSession.getUserId())).concat(String.valueOf(userSession.getTerminal()));
            boolean result = slidingWindowLimitService.passThough(userPath, windowPeriod, windowsSize);
            return result ? HttpCode.SUCCESS : HttpCode.PROGRAM_ERROR;
        } catch (Exception e) {
            logger.error("PathRuleChainService|资源限制异常|{}", e.getMessage());
            return HttpCode.PROGRAM_ERROR;
        }
    }

    @Override
    public int getOrder() {
        return pathRuleOrder == null ? RuleEnum.PATH.getCode() : pathRuleOrder;
    }

    @Override
    public String getServiceName() {
        return RuleEnum.PATH.getMesaage();
    }
}
