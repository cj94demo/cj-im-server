package com.chan.platform.common.exception;


import com.chan.platform.common.model.enums.HttpCode;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/18 10:24
 * FileName: IMException
 * Description: 自定义异常
 */
public class IMException extends RuntimeException{
    private static final long serialVersionUID = -2571805513813090624L;

    private Integer code;
    private String message;

    public IMException(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public IMException(HttpCode httpCode, String message){
        this.code = httpCode.getCode();
        this.message = message;
    }

    public IMException(HttpCode httpCode){
        this.code = httpCode.getCode();
        this.message = httpCode.getMsg();
    }

    public IMException(String message){
        this.code = HttpCode.PROGRAM_ERROR.getCode();
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
