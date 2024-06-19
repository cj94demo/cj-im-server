package com.chan.sdk.application.consumer;


import com.alibaba.fastjson.JSONObject;
import com.chan.serverdomain.constant.IMConstants;
import com.chan.serverdomain.model.IMSendResult;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/17 19:44
 * FileName: BaseMessageResultConsumer
 * Description:
 */
public class BaseMessageResultConsumer {
    protected IMSendResult<?> getResultMessage(String msg) {
        JSONObject jsonObject = JSONObject.parseObject(msg);
        String eventStr = jsonObject.getString(IMConstants.MSG_KEY);
        return JSONObject.parseObject(eventStr, IMSendResult.class);
    }
}
