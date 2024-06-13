package com.chan.serverapplication.netty.tcp.codec;

import com.chan.serverdomain.model.IMSendInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/13 21:16
 * FileName: TcpSocketMessageProtocolEncoder
 * Description: TCP消息编码
 */
public class TcpSocketMessageProtocolEncoder extends MessageToByteEncoder<IMSendInfo<?>> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, IMSendInfo<?> imSendInfo, ByteBuf byteBuf) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(imSendInfo);
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

}
