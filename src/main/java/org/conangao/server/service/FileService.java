package org.conangao.server.service;

import org.conangao.server.entity.MyFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public interface FileService {
    /**
     * 文件上传
     * @param upfile
     * @return
     */
    String fileUpload(MultipartFile upfile);

    /**
     * 文件下载
     * @param uuid
     * @return
     */
    Boolean fileDownload(String uuid, HttpServletResponse response);

    /**
     * 获取元数据
     * @param uuid
     * @return
     */
    MyFile getMetadata(String uuid);
}
