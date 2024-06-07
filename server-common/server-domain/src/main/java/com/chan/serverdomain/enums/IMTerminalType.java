package com.chan.serverdomain.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/6 21:18
 * FileName: IMTerminalType
 * Description: 发送和接收消息的终端类型
 */
public enum IMTerminalType {
    WEB(0, "web"),
    APP(1, "app");
    private Integer code;
    private String desc;

    IMTerminalType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int code() {
        return this.code;
    }

    public static List<Integer> codes() {
        return Arrays.stream(values()).map(IMTerminalType::code).collect(Collectors.toList());
    }

    public static IMTerminalType fromCode(int code) {
        for (IMTerminalType value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
