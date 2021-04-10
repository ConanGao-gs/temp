package org.conangao.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.conangao.server.entity.CommonResult;
import org.conangao.server.entity.MyFile;
import org.conangao.server.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
     * @param upfile 文件
     * @return
     */
    @PostMapping("/file/uploadServer")
    public CommonResult fileUpload(MultipartFile upfile){
        log.info("接收文件+++++++++++++++++++");
        log.info("文件类型:{}",upfile.getContentType());
        log.info("文件大小：{}",upfile.getSize());
        log.info("文件原始名：{}",upfile.getOriginalFilename());
        String uuid = fileService.fileUpload(upfile);
        if(uuid.isEmpty()){
            return new CommonResult(410,"上传失败");
        }else {
            log.info("文件上传成功，新的文件编号为：{}",uuid);
            Map res = new HashMap();
            res.put("uuid",uuid);
            return new CommonResult(200,"上传成功",res);
        }
    }

    /**
     * 文件下载
     * @param uuid 文件的UUID
     * @return
     */
    @GetMapping("/file/download/{uuid}")
    public CommonResult fileDownload(@PathVariable("uuid") String uuid, HttpServletResponse response){
        log.info("请求文件下载");
        Boolean flog = fileService.fileDownload(uuid,response);
        if(flog){
            log.info("下载成功");
            return new CommonResult(200,"下载成功");
        }
        log.info("下载失败");
        return new CommonResult(410,"下载失败");
    }

    /**
     * 获取文件元数据
     * @param uuid
     * @return
     */
    @GetMapping("/file/getMetadata/{uuid}")
    public CommonResult getMetadata(@PathVariable("uuid") String uuid){
        log.info("文件uuid是:"+uuid);
        MyFile myFile = fileService.getMetadata(uuid);
        if (myFile!=null){
            return new CommonResult(200,"获取元数据成功",myFile);
        }else {
            return new CommonResult(410,"获取元数据失败");
        }
    }
}
