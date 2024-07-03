package com.chan.platform.message.application.service;

import com.chan.platform.common.model.enums.FileType;
import com.chan.platform.common.model.vo.UploadImageVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/7/3 10:52
 * FileName: FileService
 * Description: 文件服务
 */
public interface FileService {
    /**
     * 上传文件
     */
    String uploadFile(MultipartFile file);

    /**
     * 上传图片
     */
    UploadImageVO uploadImage(MultipartFile file);

    /**
     * 生成文件URL
     */
    String getFileUrl(FileType fileTypeEnum, String fileName);

}
