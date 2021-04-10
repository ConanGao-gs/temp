package org.conangao.server.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.conangao.server.entity.MyFile;
import org.conangao.server.mapper.FileMapper;
import org.conangao.server.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceimpl implements FileService {
    @Autowired
    private FileMapper fileMapper;

    /**
     * 文件上传
     *
     * @param upfile
     * @return
     */
    @Override
    public String fileUpload(MultipartFile upfile) {
        String uuid = IdUtil.randomUUID();
        String originalFilename = upfile.getOriginalFilename();
        String fileSaveAddress = System.getProperty("user.dir") + "\\" + DateUtil.format(DateUtil.parse(DateUtil.today()), "yyyyMMdd");
        int state = fileMapper.fileUpload(upfile.getSize(), upfile.getContentType(), originalFilename, DateUtil.now(), fileSaveAddress, uuid);
        /*数据库添加成功*/
        if (state > 0) {
            /*默认上传至项目目录下*/
            try {
                File filedir = new File(fileSaveAddress);
                if (!filedir.exists() && !filedir.isDirectory()) {
                    filedir.mkdirs();
                }
                File f = new File(fileSaveAddress + "\\" + uuid + "." + upfile.getContentType());
                if (!f.exists()) {
                    f.createNewFile();
                }
                InputStream inputStream = upfile.getInputStream();
                FileOutputStream fos = new FileOutputStream(f);
                byte[] b = new byte[1024];
                int n = 0;
                while ((n = inputStream.read(b)) != -1) {
                    fos.write(b, 0, n);
                }
                fos.close();
                inputStream.close();
                log.info("文件上传成功");
                return uuid;
            } catch (Exception e) {
                log.info("文件上传失败");
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }

    /**
     * 文件下载
     *
     * @param uuid
     * @return
     */
    @Override
    public Boolean fileDownload(String uuid, HttpServletResponse response) {
        MyFile myFile = fileMapper.getFileByUUID(uuid);
        if (myFile != null) {
            log.info("文件获取成功");
            try {
                String path = myFile.getFileSaveAddress()+"\\"+myFile.getFileUUID()+"."+myFile.getFileType();
                File file = new File(path);
                String filename = file.getName();
                // 以流的形式下载文件。
                InputStream fis = new BufferedInputStream(new FileInputStream(path));
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                // 清空response
                response.reset();
                // 设置response的Header
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
                response.addHeader("Content-Length", "" + file.length());
                OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
                response.setContentType(myFile.getFileType());
                toClient.write(buffer);
                toClient.flush();
                toClient.close();

                log.info("文件下载成功");
                return true;
            } catch (Exception e) {
                log.info("文件下载失败");
                e.printStackTrace();
                return false;
            }
        }
        log.info("文件获取失败");
        return false;
    }

    /**
     * 获取元数据
     *
     * @param uuid
     * @return
     */
    @Override
    public MyFile getMetadata(String uuid) {
        return fileMapper.getFileByUUID(uuid);
    }
}
