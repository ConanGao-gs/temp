package org.conangao.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.conangao.server.entity.MyFile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Mapper
@Component(value = "FileMapper")
public interface FileMapper {

    /*测试连接*/
    MyFile testConnect();

    /**
     * 文件上传
     * @param fileSize 文件大小
     * @param fileType 文件类型
     * @param fileOriginalName 文件的原始名
     * @param createdon 创建时间
     * @param fileSaveAddress 文件保存目录地址
     * @param fileUUID 文件的uuid
     * @return
     */
    int fileUpload(long fileSize, String fileType, String fileOriginalName, String createdon, String fileSaveAddress,String fileUUID);

    /**
     * 文件下载
     * @param uuid 文件的UUID
     * @return
     */
    MyFile getFileByUUID(String uuid);
}
