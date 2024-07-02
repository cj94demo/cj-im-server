package com.chan.platform.message.interfaces.controller;

import com.chan.platform.common.model.dto.PrivateMessageDTO;
import com.chan.platform.common.model.vo.PrivateMessageVO;
import com.chan.platform.common.response.ResponseMessage;
import com.chan.platform.common.response.ResponseMessageFactory;
import com.chan.platform.message.application.service.PrivateMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/29
 * FileName: PrivateMessageController
 * Description: 私聊消息
 */
@Api(tags = "私聊消息")
@RestController
@RequestMapping("/message/private")
public class PrivateMessageController {
    @Autowired
    private PrivateMessageService privateMessageService;

    @PostMapping("/send")
    @ApiOperation(value = "发送消息", notes = "发送私聊消息")
    public ResponseMessage<Long> sendMessage(@Valid @RequestBody PrivateMessageDTO dto) {
        return ResponseMessageFactory.getSuccessResponseMessage(privateMessageService.sendMessage(dto));
    }

    @RequestMapping("/pullUnreadMessage")
    @ApiOperation(value = "拉取未读消息", notes = "拉取未读消息")
    public ResponseMessage<String> pullUnreadMessage() {
        privateMessageService.pullUnreadMessage();
        return ResponseMessageFactory.getSuccessResponseMessage();
    }

    @RequestMapping("/loadMessage")
    @ApiOperation(value = "拉取消息", notes = "拉取消息，一次最多拉取多条")
    public ResponseMessage<List<PrivateMessageVO>> loadMessage(@RequestParam Long minLd) {
        return ResponseMessageFactory.getSuccessResponseMessage(privateMessageService.loadMessage(minLd));
    }

    @RequestMapping("/history")
    @ApiOperation(value = "查询聊天记录", notes = "查询聊天记录")
    public ResponseMessage<List<PrivateMessageVO>> recallMessage(@NotNull(message = "好友id不能为空") @RequestParam Long friendId,
                                                                 @NotNull(message = "页码不能为空") @RequestParam Long page,
                                                                 @NotNull(message = "size不能为空") @RequestParam Long size) {
        return ResponseMessageFactory.getSuccessResponseMessage(privateMessageService.getHistoryMessage(friendId, page, size));
    }
}
