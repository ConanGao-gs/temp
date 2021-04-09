package org.conangao.server;

import org.conangao.server.entity.MyFile;
import org.conangao.server.mapper.FileMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileTest {
    @Autowired
    private FileMapper fileMapper;

    /**
     * 测试mybatis连接数据库
     */
    @Test
    public void MybatisToMysqlTest(){
        MyFile myFile = fileMapper.testConnect();
        System.out.println(myFile);
    }
}
