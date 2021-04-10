package org.conangao.server;

import org.conangao.server.entity.MyFile;
import org.conangao.server.mapper.FileMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

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

    /**
     * 文件下载测试
     */
    @Test
    public void fileDownloadTest() throws IOException {
        // 统一资源
        URL url = new URL("http://localhost:8081/file/download/9d60e543-0f08-46db-af92-499287b9ac92");
        // 连接类的父类，抽象类
        URLConnection urlConnection = url.openConnection();
        // http的连接类
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
        httpURLConnection.setConnectTimeout(15000);// 连接超时 单位毫秒
        httpURLConnection.setReadTimeout(15000);// 读取超时 单位毫秒
        // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
        httpURLConnection.connect();
    }

    /**
     * 根据UUID获取File
     */
    @Test
    public void getFileByUUID(){
        fileMapper.getFileByUUID("9d60e543-0f08-46db-af92-499287b9ac92");
    }
}
