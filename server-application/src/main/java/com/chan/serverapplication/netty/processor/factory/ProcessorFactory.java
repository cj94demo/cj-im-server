package com.chan.serverapplication.netty.processor.factory;

import com.chan.serverapplication.netty.processor.MessageProcessor;
import com.chan.serverapplication.netty.processor.impl.GroupMessageProcessor;
import com.chan.serverapplication.netty.processor.impl.HeartbeatProcessor;
import com.chan.serverapplication.netty.processor.impl.LoginProcessor;
import com.chan.serverapplication.netty.processor.impl.PrivateMessageProcessor;
import com.chan.serverdomain.enums.IMCmdType;
import com.chan.serverinfrastructure.holder.SpringContextHolder;


/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/13 21:21
 * FileName: ProcessorFactory
 * Description: 处理器工厂类
 */
public class ProcessorFactory {
    public static MessageProcessor<?> getProcessor(IMCmdType cmd){
        switch (cmd){
            //登录
            case LOGIN:
                return SpringContextHolder.getApplicationContext().getBean(LoginProcessor.class);
            //心跳
            case HEART_BEAT:
                return SpringContextHolder.getApplicationContext().getBean(HeartbeatProcessor.class);
            //单聊消息
            case PRIVATE_MESSAGE:
                return SpringContextHolder.getApplicationContext().getBean(PrivateMessageProcessor.class);
            //群聊消息
            case GROUP_MESSAGE:
                return SpringContextHolder.getApplicationContext().getBean(GroupMessageProcessor.class);
            default:
                return null;

        }
    }

}
