package com.chan.platform.common.risk.rule.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.chan.platform.common.model.enums.HttpCode;
import com.chan.platform.common.risk.enums.RuleEnum;
import com.chan.platform.common.risk.rule.service.RuleChainService;
import com.chan.platform.common.risk.rule.service.base.BaseRuleChainService;
import com.chan.platform.common.utils.XssUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/18 15:39
 * FileName: XssRuleChainService
 * Description: Xss安全服务规则
 */
@Component
public class XssRuleChainService extends BaseRuleChainService implements RuleChainService {
    private final Logger logger = LoggerFactory.getLogger(XssRuleChainService.class);

    @Value("${bh.im.rule.xssRule.enabled}")
    private Boolean xssRuleEnabled;

    @Value("${bh.im.rule.xssRule.order}")
    private Integer xssRuleOrder;

    @Override
    public HttpCode execute(HttpServletRequest request, Object handler) {
        //未开启XSS规则，直接通过校验
        if (BooleanUtil.isFalse(xssRuleEnabled)) {
            return HttpCode.SUCCESS;
        }
        // 检查参数
        Map<String, String[]> paramMap = request.getParameterMap();
        for (String[] values : paramMap.values()) {
            for (String value : values) {
                if (XssUtils.checkXss(value)) {
                    return HttpCode.XSS_PARAM_ERROR;
                }
            }
        }
        // 检查body
        String body = getBody(request);
        if (XssUtils.checkXss(body)) {
            return HttpCode.XSS_PARAM_ERROR;
        }
        return HttpCode.SUCCESS;
    }

    @Override
    public int getOrder() {
        return xssRuleOrder == null ? RuleEnum.XSS.getCode() : xssRuleOrder;
    }

    @Override
    public String getServiceName() {
        return RuleEnum.XSS.getMesaage();
    }

    private String getBody(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            logger.error("XssInterceptor.getBody|获取请求体异常:{}", e.getMessage());
        }
        return sb.toString();
    }
}
