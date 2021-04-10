package org.conangao.client.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.conangao.client.entity.CommonResult;
import org.conangao.client.entity.MyRequest;
import org.conangao.client.entity.MyResponse;
import org.conangao.client.service.FileService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class FileServiceimpl implements FileService {
    /**
     * 文件上传
     * @param filePath 文件路径
     * @return
     */
    public CommonResult fileUpload(String filePath){
        File file = new File(filePath);
        String inputName = "upfile";
        String filename = file.getName();
        // boundary就是request头和上传文件内容的分隔符
        String BOUNDARY = "---------------------------123821742118716";
        MyResponse res = MyRequest.builder()
                .setUrl("http://localhost:8081/file/uploadServer")
                .setReadTimeout(30000)
                .setConnectTimeout(5000)
                .setDoInput(true)
                .setDoOutput(true)
                .addHeader("Connection", "Keep-Alive")
                .addHeader("Content-Type", "multipart/form-data; boundary=" + BOUNDARY)
                .addPostData("upfile", filePath)
                .post();
        if (res.getResponseCode() == 200) {
            log.info("文件上传成功");
            log.info("返回的UUID"+res.getText());
            return new CommonResult(200,"文件上传成功",res.getText());
        }
        log.info("文件上传失败");
        return new CommonResult(410,"文件上传失败");
    }

    /**
     * 文件下载
     * @param uuid 文件uuid
     * @return
     */
    public CommonResult fileDownload(String uuid) throws IOException {
        MyResponse res = MyRequest.builder()
                .setUrl("http://localhost:8081/file/download/"+uuid)
                .setReadTimeout(30000)
                .setConnectTimeout(5000)
                .setDoInput(true)
                .setDoOutput(true)
                .addHeader("Connection", "Keep-Alive")
                .get();
        if (res.getResponseCode() == 200) {
            String downDir = System.getProperty("user.dir") + "\\download";
            File file = new File(downDir);
            if(!file.exists() &&!file.isDirectory()){
                file.mkdirs();
            }
            File filedir = new File(downDir + "\\"+uuid+"." + res.getContentType());
            if (!filedir.exists()){
                filedir.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(filedir);
            fileOutputStream.write(res.getByteContent());
            fileOutputStream.close();
            log.info("文件下载成功");
            return new CommonResult(200,"文件下载成功");
        }
        log.info("文件下载失败");
        return new CommonResult(410,"文件下载失败");
    }

    /**
     * 获取元数据
     * @param uuid 文件uuid
     * @return
     */
    public CommonResult getMetaData(String uuid){
        MyResponse res = MyRequest.builder()
                .setUrl("http://localhost:8081/file/getMetadata/"+uuid)
                .setReadTimeout(30000)
                .setConnectTimeout(5000)
                .setDoInput(true)
                .setDoOutput(true)
                .addHeader("Connection", "Keep-Alive")
                .get();
        if (res.getResponseCode() == 200) {
            log.info("获取元数据成功");
            return new CommonResult(200,"获取元数据成功",res.getText());
        }
        return new CommonResult(410,"获取元数据失败");
    }
}
