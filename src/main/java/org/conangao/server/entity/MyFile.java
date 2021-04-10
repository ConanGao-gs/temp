package org.conangao.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyFile {
    /*文件id*/
    private Long id;
    /*文件大小*/
    private String fileSize;
    /*文件类型*/
    private String fileType;
    /*文件名*/
    private String fileOriginalName;
    /*创建日期*/
    private String createdon;
    /*文件保存目录地址*/
    private String fileSaveAddress;
    /*文件UUID*/
    private String fileUUID;
}
