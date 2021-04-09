package org.conangao.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyFile {
    private Long id;
    private String fileSize;
    private String fileType;
    private String fileOriginalName;
    private String createdon;
    private String fileSaveAddress;
}
