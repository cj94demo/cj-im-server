package com.chan.platform.message.interfaces.controller;

import com.chan.platform.common.model.enums.HttpCode;
import com.chan.platform.common.model.vo.UploadImageVO;
import com.chan.platform.common.response.ResponseMessage;
import com.chan.platform.common.response.ResponseMessageFactory;
import com.chan.platform.message.application.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/7/3 11:11
 * FileName: FileController
 * Description: 文件接口
 */
@RestController
@Api(tags = "文件上传")
public class FileController {
    @Autowired
    private FileService fileService;

    @ApiOperation(value = "上传图片", notes = "上传图片,上传后返回原图和缩略图的url")
    @PostMapping("/image/upload")
    public ResponseMessage<UploadImageVO> uploadImage(MultipartFile file) {
        return ResponseMessageFactory.getSuccessResponseMessage(fileService.uploadImage(file));
    }

    @ApiOperation(value = "上传文件", notes = "上传文件，上传后返回文件url")
    @PostMapping("/file/upload")
    public ResponseMessage<String> uploadFile(MultipartFile file) {
        return ResponseMessageFactory.getSuccessResponseMessage(fileService.uploadFile(file), HttpCode.SUCCESS.getMsg());
    }

}
