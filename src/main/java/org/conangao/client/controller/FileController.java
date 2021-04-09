package org.conangao.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.conangao.client.entity.CommonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 文件controller
 */
@Controller
@Slf4j
public class FileController {

    /**
     * 文件上传
     * @param multipartFile 文件
     * @return
     */
    public CommonResult fileUpload(MultipartFile multipartFile){

        return null;
    }

    /**
     * 文件下载
     * @param uuid 文件的UUID
     * @return
     */
    public CommonResult fileDownload(UUID uuid){
        return null;
    }

    /**
     * 获取文件元数据
     * @param uuid
     * @return
     */
    public CommonResult getMetadata(UUID uuid){
        return null;
    }
}
