package com.chan.platform.common.model.enums;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/18 9:57
 * FileName: HttpCode
 * Description: 结果状态码
 */
public enum HttpCode {
    SUCCESS(200,"成功"),
    NO_LOGIN(400,"未登录"),
    INVALID_TOKEN(401,"token无效或已过期"),
    PARAMS_ERROR(402,"参数错误"),
    PROGRAM_ERROR(500,"系统繁忙，请稍后再试"),
    PASSWOR_ERROR(10001,"密码不正确"),
    USERNAME_ALREADY_REGISTER(10003,"该用户名已注册"),
    XSS_PARAM_ERROR(10004,"请不要输入非法内容");

    private int code;
    private String msg;

    HttpCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
