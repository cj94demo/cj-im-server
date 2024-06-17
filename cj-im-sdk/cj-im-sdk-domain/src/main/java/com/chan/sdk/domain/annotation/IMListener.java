package com.chan.sdk.domain.annotation;

import com.chan.serverdomain.enums.IMListenerType;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/17 16:28
 * FileName: IMListener
 * Description: 监听消息注解
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface IMListener {
    IMListenerType listenerType();
}
