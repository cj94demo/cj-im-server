package com.chan.serverdomain.exception;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/13 21:50
 * FileName: IMException
 * Description: 自定义异常
 */
public class IMException extends RuntimeException {
    private Integer code;

    public IMException(String message) {
        super(message);
    }

    public IMException(Integer code, String messgae) {
        super(messgae);
        this.code = code;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
