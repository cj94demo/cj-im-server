package com.chan.platform.common.exception;

import com.chan.platform.common.response.ResponseMessage;
import com.chan.platform.common.response.ResponseMessageFactory;
import com.chan.platform.domain.model.enums.HttpCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/18 10:26
 * FileName: IMExceptionHandler
 * Description: 全局异常处理器
 */
@ControllerAdvice
public class IMExceptionHandler {
    @ResponseBody
    @ExceptionHandler(IMException.class)
    public ResponseMessage<String> handleIMException(IMException e) {
        return ResponseMessageFactory.getErrorResponseMessage(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseMessage<String> handleException(Exception e) {
        return ResponseMessageFactory.getErrorResponseMessage(HttpCode.PROGRAM_ERROR);
    }
}
