package com.chan.serverapplication.netty.runner;

import cn.hutool.core.collection.CollectionUtil;
import com.chan.serverapplication.netty.IMNettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.List;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/13 21:36
 * FileName: IMServerRunner
 * Description: 服务组
 */
@Component
public class IMServerRunner implements CommandLineRunner {
    @Autowired
    private List<IMNettyServer> imNettyServers;

    /**
     * 判断服务是否准备完毕
     */
    public boolean isReady() {
        for (IMNettyServer imNettyServer : imNettyServers) {
            if (!imNettyServer.isReady()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void run(String... args) throws Exception {
        //启动每个服务
        if (!CollectionUtil.isEmpty(imNettyServers)) {
            imNettyServers.forEach(IMNettyServer::start);
        }
    }

    @PreDestroy
    public void destroy() {
        if (CollectionUtil.isNotEmpty(imNettyServers)) {
            imNettyServers.forEach(IMNettyServer::shutdown);
        }
    }

}
