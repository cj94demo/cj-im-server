package com.chan.serverapplication.netty.tcp.codec;

import com.chan.serverdomain.constant.IMConstants;
import com.chan.serverdomain.model.IMSendInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/13 21:15
 * FileName: TcpSocketMessageProtocolDecoder
 * Description: TCP消息解码类
 */
public class TcpSocketMessageProtocolDecoder extends ReplayingDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < IMConstants.MIN_READABLE_BYTES) {
            return;
        }
        //获取数据包长度
        int length = byteBuf.readInt();
        ByteBuf contentBuf = byteBuf.readBytes(length);
        String content = contentBuf.toString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        IMSendInfo imSendInfo = objectMapper.readValue(content, IMSendInfo.class);
        list.add(imSendInfo);
    }

}
