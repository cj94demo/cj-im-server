package com.chan.platform.common.session;

import com.chan.platform.common.model.contants.IMPlatformConstants;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/18 10:23
 * FileName: SessionContext
 * Description: session上下文
 */
public class SessionContext {
    public static UserSession getSession(){
        // 从请求上下文里获取Request对象
        ServletRequestAttributes requestAttributes = ServletRequestAttributes.class.
                cast(RequestContextHolder.getRequestAttributes());
        HttpServletRequest request = requestAttributes.getRequest();
        return  (UserSession) request.getAttribute(IMPlatformConstants.SESSION);
    }
}
