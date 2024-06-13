package com.chan.serverapplication.netty.ws.codec;

import com.chan.serverdomain.model.IMSendInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/13 21:39
 * FileName: WebSocketMessageProtocolEncoder
 * Description: WS编码器
 */
public class WebSocketMessageProtocolEncoder extends MessageToMessageEncoder<IMSendInfo> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, IMSendInfo imSendInfo, List<Object> list) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String text = objectMapper.writeValueAsString(imSendInfo);
        TextWebSocketFrame frame = new TextWebSocketFrame(text);
        list.add(frame);
    }
}
