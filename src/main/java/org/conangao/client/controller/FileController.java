package org.conangao.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.conangao.client.entity.CommonResult;
import org.conangao.client.entity.MyRequest;
import org.conangao.client.entity.MyResponse;
import org.conangao.client.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件controller
 */
@RestController
@Slf4j
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     * @param filePath 文件路径
     * @return
     */
    @PostMapping("/file/upload")
    public CommonResult fileUpload(@RequestParam("filePath") String filePath){
        log.info("获取到的文件路径：{}",filePath);
        return fileService.fileUpload(filePath);
    }

    /**
     * 文件下载
     * @param uuid 文件的UUID
     * @return
     */
    @GetMapping("/file/download/{uuid}")
    public CommonResult fileDownload(@PathVariable("uuid") String uuid) throws IOException {
        return fileService.fileDownload(uuid);
    }

    /**
     * 获取文件元数据
     * @param uuid
     * @return
     */
    @GetMapping("/file/getMetadata/{uuid}")
    public CommonResult getMetadata(@PathVariable("uuid") String uuid){
        return fileService.getMetaData(uuid);
    }
}
