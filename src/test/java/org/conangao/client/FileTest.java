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
}
