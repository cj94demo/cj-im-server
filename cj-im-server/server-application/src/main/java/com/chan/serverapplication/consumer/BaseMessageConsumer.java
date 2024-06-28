package com.chan.serverapplication.consumer;

import com.alibaba.fastjson.JSONObject;
import com.chan.serverdomain.constant.IMConstants;
import com.chan.serverdomain.model.IMReceiveInfo;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/13 21:42
 * FileName: BaseMessageConsumer
 * Description: 基础消息消费者
 */
public class BaseMessageConsumer {
    /**
     * 解析数据
     */
    protected IMReceiveInfo getReceiveMessage(String msg) {
        JSONObject jsonObject = JSONObject.parseObject(msg);
        String eventStr = jsonObject.getString(IMConstants.MSG_KEY);
        return JSONObject.parseObject(eventStr, IMReceiveInfo.class);
    }

}
