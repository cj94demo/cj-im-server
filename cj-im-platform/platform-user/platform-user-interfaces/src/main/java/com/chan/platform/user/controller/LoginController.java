package com.chan.platform.user.controller;

import com.chan.platform.common.model.dto.LoginDTO;
import com.chan.platform.common.model.dto.RegisterDTO;
import com.chan.platform.common.model.vo.LoginVO;
import com.chan.platform.common.response.ResponseMessage;
import com.chan.platform.common.response.ResponseMessageFactory;
import com.chan.platform.user.application.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/18 19:41
 * FileName: LoginController
 * Description: 用户登录认证授权相关接口
 */
@Api(tags = "用户登录和注册")
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "用户注册", notes = "用户注册")
    public ResponseMessage<LoginVO> register(@Valid @RequestBody LoginDTO dto) {
        LoginVO vo = userService.login(dto);
        return ResponseMessageFactory.getSuccessResponseMessage(vo);
    }


    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "用户注册")
    public ResponseMessage<String> register(@Valid @RequestBody RegisterDTO dto) {
        userService.register(dto);
        return ResponseMessageFactory.getSuccessResponseMessage();
    }
}
