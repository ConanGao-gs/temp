package org.conangao.client;

import org.conangao.client.entity.MyRequest;
import org.conangao.client.entity.MyResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class FileTest {

    //region http封装类的测试

    /**
     * 发送get请求
     */
    @Test
    public void sendGetRequest() {
        MyResponse res = MyRequest.builder()
                .setUrl("http://httpbin.org")
                .addPathVariable("get")
                .addQuery("keyword", "csdn")
                .addHeader("User-Agent", "Chrome")
                .addHeader("Content-Type", "text/html")
                .addCookies("JSESSIONID=2454;aie=adf")
                .addCookie("username", "bin")
                .get();

        if (res.getResponseCode() == 200) {
            System.out.println(res.getText());
        }
    }

    /**
     * 发送post请求并携带表单数据
     */
    @Test
    public void sendPostRequestWithFormData() {
        MyResponse res = MyRequest.builder()
                .setUrl("http://httpbin.org")
                .addPathVariable("post")
                .addQuery("keyword", "csdn")
                .addHeader("User-Agent", "Chrome")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addCookies("JSESSIONID=2454;aie=adf")
                .addCookie("yes", "no")
                .addPostData("username", "zhangsan")
                .addPostData("password", "154134513")
                .addPostData("gender", "male")
                .addPostData("age", 18)
                .post();

        if (res.getResponseCode() == 200) {
            System.out.println(res.getText());
        }
    }

    /**
     * 发送post请求携带JSON格式数据
     */
    @Test
    public void sendPostRequestWithJSON() {
        Map data = new HashMap();

        Map keyword1 = new HashMap();
        keyword1.put("value", "98gadf");
        data.put("keyword1", keyword1);

        Map keyword2 = new HashMap();
        keyword1.put("value", "9fghfsgdf");
        data.put("keyword2", keyword1);

        List list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add("c");


        MyResponse res = MyRequest.builder()
                .setUrl("http://httpbin.org")
                .addPathVariable("post")
                .addHeader("User-Agent", "Chrome")
                .addHeader("Content-Type", "application/json")
                .addPostData("touser", "orafd98bu")
                .addPostData("template_id", "34589u")
                .addPostData("page", 1)
                .addPostData("form_id", 345)
                .addPostData("data", data)
                .addPostData("list", list)
                .post();

        if (res.getResponseCode() == 200) {
            System.out.println(res.getText());
        }
    }

    /**
     * 保存二进制文件
     * @throws IOException
     */
    @Test
    public void saveBinaryFile() throws IOException {
        MyResponse res = MyRequest.builder()
                .setUrl("https://img-blog.csdnimg.cn/20190324160739729.png")
                .addHeader("User-Agent", "Chrome")
                .get();

        /*在当前目录下生成文件*/
        if (res.getResponseCode() == 200){
            OutputStream os = new BufferedOutputStream(
                    new FileOutputStream(
                            new File("photo.png")
                    )
            );
            os.write(res.getByteContent());
        }
    }
    // endregion

    /**
     * 文件上传
     */
    @Test
    public void fileUpload() throws IOException {
        String inputValue = "D:/1.png";
        File file = new File(inputValue);
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
                .addPostData("upfile", inputValue)
                .post();
        if (res.getResponseCode() == 200) {
            System.out.println(res.getText());
        }
    }

    /**
     * 下载测试
     */
    @Test
    public void fileDownload() throws IOException {
        MyResponse res = MyRequest.builder()
                .setUrl("http://localhost:8081/file/download/9d60e543-0f08-46db-af92-499287b9ac92")
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
            File filedir = new File(downDir + "\\9d60e543-0f08-46db-af92-499287b9ac92." + res.getContentType());
            if (!filedir.exists()){
                filedir.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(filedir);
            fileOutputStream.write(res.getByteContent());
            fileOutputStream.close();
        }
    }

    /**
     * 获取元数据
     */
    @Test void getMetaData(){
        MyResponse res = MyRequest.builder()
                .setUrl("http://localhost:8081/file/getMetadata/9d60e543-0f08-46db-af92-499287b9ac92")
                .setReadTimeout(30000)
                .setConnectTimeout(5000)
                .setDoInput(true)
                .setDoOutput(true)
                .addHeader("Connection", "Keep-Alive")
                .get();
        if (res.getResponseCode() == 200) {
            System.out.println(res.getText());
        }
    }
}
