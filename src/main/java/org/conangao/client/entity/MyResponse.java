package org.conangao.client.entity;

import java.io.*;
import java.net.HttpURLConnection;
import java.security.Permission;
import java.util.List;
import java.util.Map;

/**
 * 封装Response
 */
public class MyResponse {
    private byte[] byteContent;
    private String errorMsg;
    private int responseCode;
    private HttpURLConnection conn;

    public MyResponse(HttpURLConnection conn, String encode) {
        this.conn = conn;
        BufferedInputStream bis = null;
        ByteArrayOutputStream baos = null;

        try {
            bis = new BufferedInputStream(conn.getInputStream());
            baos = new ByteArrayOutputStream();

            byte[] bytes = new byte[2048];
            int len = 0;
            while (-1 != (len = bis.read(bytes))) {
                baos.write(bytes, 0, len);
            }

            byteContent = baos.toByteArray();
            responseCode = conn.getResponseCode();

        } catch (IOException e) {
            e.printStackTrace();
            BufferedReader in = null;
//            错误信息
            try {
                StringBuffer msg = new StringBuffer(30);
                in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), encode));

                String line = null;
                while ((line = in.readLine()) != null) {
                    msg.append(line);
                }

                this.errorMsg = msg.toString();
                responseCode = -1;
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public byte[] getByteContent() {
        return byteContent;
    }

    public String getText(){
        return byteContent == null ? "" : new String(byteContent);
    }


    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public String toString() {
        if (errorMsg != null)
            return errorMsg;

        if (byteContent != null) {
            return new String(byteContent);
        }
        return "";
    }

    public String getHeaderFieldKey(int n) {
        return conn.getHeaderFieldKey(n);
    }

    public String getHeaderField(int n) {
        return conn.getHeaderField(n);
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage(){
        try {
            return conn.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getHeaderFieldDate(String name, long Default) {
        return conn.getHeaderFieldDate(name, Default);
    }

    public Permission getPermission(){
        try {
            return conn.getPermission();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getContentLength() {
        return conn.getContentLength();
    }

    public long getContentLengthLong() {
        return conn.getContentLengthLong();
    }

    public String getContentType() {
        return conn.getContentType();
    }

    public String getContentEncoding() {
        return conn.getContentEncoding();
    }

    public long getExpiration() {
        return conn.getExpiration();
    }

    public long getDate() {
        return conn.getDate();
    }


    public String getHeaderField(String name) {
        return conn.getHeaderField(name);
    }

    public Map<String, List<String>> getHeaderFields() {
        return conn.getHeaderFields();
    }


    public int getHeaderFieldInt(String name, int Default) {
        return conn.getHeaderFieldInt(name, Default);
    }

    public long getHeaderFieldLong(String name, long Default) {
        return conn.getHeaderFieldLong(name, Default);
    }

    public HttpURLConnection getConn() {
        return conn;
    }
}
