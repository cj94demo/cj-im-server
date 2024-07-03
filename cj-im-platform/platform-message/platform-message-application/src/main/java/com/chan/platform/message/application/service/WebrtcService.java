package com.chan.platform.message.application.service;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/7/3 16:48
 * FileName: WebrtcService
 * Description: WebRTC
 */
public interface WebrtcService {
    /**
     * 视频呼叫
     */
    void call(Long uid, String offer);

}
