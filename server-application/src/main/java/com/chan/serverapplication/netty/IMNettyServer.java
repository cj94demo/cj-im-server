package com.chan.serverapplication.netty;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/13 20:49
 * FileName: IMNettyServer
 * Description: IM Netty Server
 */
public interface IMNettyServer {
    boolean isReady();

    void start();

    void shutdown();
}
