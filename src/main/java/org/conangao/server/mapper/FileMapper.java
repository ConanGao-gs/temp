package org.conangao.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.conangao.server.entity.MyFile;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "FileMapper")
public interface FileMapper {

    MyFile testConnect();
}
