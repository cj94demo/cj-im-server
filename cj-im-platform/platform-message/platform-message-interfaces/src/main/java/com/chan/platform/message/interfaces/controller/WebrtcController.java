package com.chan.platform.message.interfaces.controller;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/7/3 17:06
 * FileName: WebrtcController
 * Description: Web RTC
 */

import com.chan.platform.common.response.ResponseMessage;
import com.chan.platform.common.response.ResponseMessageFactory;
import com.chan.platform.message.application.service.WebrtcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "webrtc视频通话")
@RestController
@RequestMapping("/webrtc/private")
public class WebrtcController {
    @Autowired
    private WebrtcService webrtcService;

    @ApiOperation(httpMethod = "POST", value = "呼叫视频通话")
    @PostMapping("/call")
    public ResponseMessage<String> call(@RequestParam Long uid, @RequestBody String offer) {
        webrtcService.call(uid, offer);
        return ResponseMessageFactory.getSuccessResponseMessage();
    }

}
