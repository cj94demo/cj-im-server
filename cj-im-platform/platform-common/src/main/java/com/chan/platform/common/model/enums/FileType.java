package com.chan.platform.common.model.enums;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/18 9:57
 * FileName: FileType
 * Description: 文件类型
 */
public enum FileType {
    FILE(0,"/file/", "文件"),
    IMAGE(1,"/image/", "图片"),
    VIDEO(2,"/video/", "视频"),
    AUDIO(3, "audio",  "语音");

    private final Integer code;
    private final String path;
    private final String desc;

    FileType(Integer code, String path, String desc) {
        this.code = code;
        this.path = path;
        this.desc = desc;
    }

    public Integer code(){
        return this.code;
    }

    public String getPath() {
        return path;
    }

}
