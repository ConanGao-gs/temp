package org.conangao.client.service;


import org.conangao.client.entity.CommonResult;

import java.io.IOException;

public interface FileService {
    /**
     * 文件上传
     * @param filePath 文件路径
     * @return
     */
    CommonResult fileUpload(String filePath);

    /**
     * 文件下载
     * @param uuid 文件uuid
     * @return
     */
    CommonResult fileDownload(String uuid) throws IOException;

    /**
     * 获取元数据
     * @param uuid 文件uuid
     * @return
     */
    CommonResult getMetaData(String uuid);
}
