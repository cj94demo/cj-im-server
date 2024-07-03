package com.chan.platform.message.application.ice;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/7/3 16:50
 * FileName: ICEServerConfig
 * Description: ICE服务配置
 */
@Component
@ConfigurationProperties(prefix = "webrtc")
public class ICEServerConfig {
    private List<ICEServer> iceServers = new ArrayList<>();

    public List<ICEServer> getIceServers() {
        return iceServers;
    }

    public void setIceServers(List<ICEServer> iceServers) {
        this.iceServers = iceServers;
    }

}
