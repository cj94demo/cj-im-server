package com.chan.platform.dubbo.openai.service;

import java.io.IOException;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/24 10:44
 * FileName: OpenAIDubboService
 * Description: 对外提供的Dubbo接口
 */
public interface OpenAIDubboService {
    /**
     * 向大模型发送数据，并接收返回结果
     *
     * @param requestData 发送的数据
     * @return 大模型返回的结果
     * @throws IOException
     */
    String sendMessage(String requestData) throws IOException;
}
