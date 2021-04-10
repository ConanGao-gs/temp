package org.conangao.client.entity;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 封装Request类
 */
public class MyRequest {
    // 几种method方法
    public static final class Method {
        private Method() {
        }

        public static final String GET = "GET";
        public static final String POST = "POST";
        public static final String HEAD = "HEAD";
        public static final String OPTIONS = "OPTIONS";
        public static final String PUT = "PUT";
        public static final String DELETE = "DELETE";
        public static final String TRACE = "TRACE";
    }

    // 常见编码
    public static final class Encode {
        private Encode() {
        }

        public static final String UTF8 = "UTF-8";
        public static final String UTF16 = "UTF-16";
        public static final String GB18030 = "GB18030";
        public static final String GBK = "GBK";
        public static final String GB2312 = "GB2312";
        public static final String ISO_8859_1 = "ISO-8859-1";
        public static final String ASCII = "ASCII";
    }

    // 访问目标
    private String url;
    // 路径参数
    private Map<String, Object> query = new HashMap();
    // 访问method
    private String method = Method.GET;
    // Post data参数
    private Map postData = new HashMap();
    // Cookies 注：若headers中和属性cookies同时设置，属性cookies将会覆盖headers中的cookies
    private String cookies = "";
    // 头信息
    private Map<String, String> headers = new HashMap<String, String>();
    // 返回结果的编码
    private String encode = Encode.UTF8;
    // 返回连接对象
    private HttpURLConnection conn;
    // 请求后是否关闭连接
    private boolean isCloseConnectionAfterRequest = true;

    private boolean doInput = true;
    private boolean doOutput = false;
    private int readTimeout = -1;
    private int connectTimeout = -1;
    private boolean useCaches = false;

    public MyRequest() {
    }

    public MyRequest(String url) {
        this.url = url;
    }

    public static MyRequest builder() {
        return new MyRequest();
    }

    public MyRequest build() {
        return this;
    }

    private HttpURLConnection getConnection() {
        this.openConnection();
        return this.conn;
    }

    private void openConnection() {
        if (url == null || "".equals(url))
            throw new RuntimeException("url不能为空");

        try {
            // 构造url
            URL url = new URL(this.url + toQueryString());
            // 获取连接对象
            this.conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MyResponse request() {

        MyResponse MyResponse = null;
        openConnection();

        // 设置请求参数
        try {
            setRequestProperties(conn);
            // 请求
            MyResponse = new MyResponse(conn, encode);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isCloseConnectionAfterRequest && conn != null) {
                conn.disconnect();
            }
        }

        return MyResponse;
    }

    /**
     * 以GET方式访问
     * ！会覆盖之前设置的method
     *
     * @return
     */
    public MyResponse get() {
        this.method = Method.GET;
        return request();
    }

    /**
     * 以POST方式访问
     * ！会覆盖之前设置的method
     *
     * @return
     */
    public MyResponse post() {
        this.method = Method.POST;
        return request();
    }

    /**
     * 以PUT方式访问
     * ！会覆盖之前设置的method
     *
     * @return
     */
    public MyResponse put() {
        this.method = Method.PUT;
        return request();
    }

    /**
     * 以DELETE方式访问
     * ！会覆盖之前设置的method
     *
     * @return
     */
    public MyResponse delete() {
        this.method = Method.DELETE;
        return request();
    }

    /**
     * 静态请求方法。
     * params按顺序依次是query, postData, method, headers, cookies
     *
     * @param url
     * @param params
     * @return
     */
    public static MyResponse request(String url, Object... params) {
        MyRequest MyRequest = new MyRequest(url);
        parseParams(MyRequest, params);

        return MyRequest.request();
    }

    /**
     * 静态get请求方法。
     *
     * @param url    目标url
     * @param params 按顺序依次为query, headers, cookies
     * @return
     */
    public static MyResponse get(String url, Object... params) {
        Object query = null;
        Object headers = null;
        Object cookies = null;

        //参数长度
        int paramLength = params.length;
        if (paramLength > 0)
            query = params[0];
        if (paramLength > 1)
            headers = params[1];
        if (paramLength > 2)
            cookies = params[3];

        return request(url, query, null, Method.GET, headers, cookies);
    }

    /**
     * 静态post请求方法。
     *
     * @param url    目标url
     * @param params 按顺序依次为query, postData, headers, cookies
     * @return
     */
    public static MyResponse post(String url, Object... params) {
        Object query = null;
        Object postData = null;
        Object headers = null;
        Object cookies = null;

        //参数长度
        int paramLength = params.length;
        if (paramLength > 0)
            query = params[0];
        if (paramLength > 1)
            postData = params[1];
        if (paramLength > 2)
            headers = params[2];
        if (paramLength > 3)
            cookies = params[3];

        return request(url, query, postData, Method.POST, headers, cookies);
    }

    private static void parseParams(MyRequest MyRequest, Object[] params) {
        //参数长度
        int paramLength = params.length;
        if (paramLength > 0) {
            Object param = params[0];
            if (param instanceof Map)
                MyRequest.setQuery((Map) param);
        }
        if (paramLength > 1) {
            Object param = params[1];
            if (param instanceof Map)
                MyRequest.setPostData((Map) param);
        }
        if (paramLength > 2) {
            Object param = params[2];
            if (param instanceof String)
                MyRequest.setMethod((String) param);
        }
        if (paramLength > 3) {
            Object param = params[3];
            if (param instanceof Map)
                MyRequest.setHeaders((Map) param);
        }
        if (paramLength > 4) {
            Object param = params[4];
            if (param instanceof String)
                MyRequest.setCookies((String) param);
        }
    }

    /**
     * 设置请求属性
     *
     * @param conn
     * @throws IOException
     */
    private void setRequestProperties(HttpURLConnection conn) throws IOException {
        conn.setDoInput(doInput);
        conn.setDoOutput(doOutput);
        conn.setUseCaches(useCaches);
        if (readTimeout > 0)
            conn.setReadTimeout(readTimeout);
        if (connectTimeout > 0)
            conn.setConnectTimeout(connectTimeout);

        conn.setRequestMethod(method);

        if (!headers.isEmpty()) {
            for (Map.Entry<String, String> header : headers.entrySet())
                conn.setRequestProperty(header.getKey(), header.getValue());
        }

        if (cookies != null && !"".equals(cookies.trim())) {
            conn.setRequestProperty("Cookie", cookies);
        }

        if (Method.POST.equals(method.toUpperCase())) {
            conn.setUseCaches(false);
            // indicates that the application intends to write postData to the URL connection.
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();

            String contentType = headers.get("Content-Type");
            if (contentType != null && contentType.contains("multipart/form-data")) {
                File file = new File((String) postData.get("upfile"));
                String filename = file.getName();
                StringBuffer strBuf = new StringBuffer();
                String BOUNDARY = "---------------------------123821742118716";
                strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                strBuf.append("Content-Disposition: form-data; name=\"" + "upfile" + "\"; filename=\"" + filename + "\"\r\n");
                strBuf.append("Content-Type:" + filename.substring(filename.indexOf(".")+1) + "\r\n\r\n");
                os.write(strBuf.toString().getBytes());
                DataInputStream in = new DataInputStream(new FileInputStream(file));
                int bytes = 0;
                byte[] bufferOut = new byte[1024];
                while ((bytes = in.read(bufferOut)) != -1) {
                    os.write(bufferOut, 0, bytes);
                }
                byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
                os.write(endData);
            }else {
                os.write(toPostDataString().getBytes());
            }
            os.flush();
        }
    }

    private String toPostDataString() {
        if (postData.size() < 1)
            return "";
        String postDataString = null;

        String contentType = headers.get("Content-Type");

        if (contentType != null && contentType.contains("form")) {
            postDataString = toQueryString(this.postData);
            postDataString = postDataString.substring(1, postDataString.length());
        }
//        else if (ContentType.contains("json"))
        else {
            postDataString = toJSONString(this.postData);
        }

        return postDataString;
    }

    /**
     * 查询参数转为字符串
     *
     * @return
     */
    private String toQueryString(Map<String, Object> query) {
        StringBuilder sb = new StringBuilder();
        if (!query.isEmpty()) {
            if (url.contains("?"))
                sb.append("&");
            else
                sb.append("?");

            for (Map.Entry entry : query.entrySet())
                sb.append(entry.getKey().toString() + "=" + entry.getValue().toString() + "&");
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }

    /**
     * 查询参数转为字符串
     *
     * @return
     */
    private String toQueryString() {
        return this.toQueryString(this.query);
    }

    /**
     * 添加一条路径参数
     *
     * @param var 路径参数
     * @return
     */
    public MyRequest addPathVariable(Object var) {
        this.url += "/" + var;
        return this;
    }

    /**
     * 添加一条路径参数
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public MyRequest addQuery(String key, Object value) {
        if (key == null || value == null)
            throw new IllegalArgumentException("key或value不可为空");

        query.put(key, value);
        return this;
    }

    /**
     * 添加一条body参数
     *
     * @param key
     * @param value
     * @return
     */
    public MyRequest addPostData(String key, Object value) {
        if (key == null || value == null)
            throw new IllegalArgumentException("key或value不可为空");

        postData.put(key, value);
        return this;
    }

    /**
     * 添加一条cookie
     *
     * @param key
     * @param value
     * @return
     */
    public MyRequest addCookie(String key, String value) {
        if (key == null || value == null)
            throw new IllegalArgumentException("key或value不可为空");

        cookies = cookies.trim() + ("".equals(cookies.trim()) ? "" : ";") + key + "=" + value;
        return this;
    }

    public MyRequest addCookies(String cookie) {
        if (cookie == null)
            throw new IllegalArgumentException("cookie不可为空");

        this.cookies = this.cookies.trim() + ("".equals(this.cookies.trim()) ? "" : ";") + cookie;
        return this;
    }

    /**
     * 添加一条header
     *
     * @param key
     * @param value
     * @return
     */
    public MyRequest addHeader(String key, String value) {
        if (key == null || "".equals(key) || value == null)
            throw new IllegalArgumentException("key或value不可为空");

        headers.put(key, value);
        return this;
    }


    //<editor-fold desc="工具方法">

    /**
     * 转为Json字符串
     *
     * @param obj Map或List类型
     * @return
     */
    public String toJSONString(Object obj) {
        if (obj instanceof Map) {
            Map dataMap = (Map) obj;
            if (dataMap == null) {
                return "{}";
            }

            StringBuffer sb = new StringBuffer();
            Iterator entries = dataMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                sb.append("\"" + entry.getKey() + "\":");

                Object value = entry.getValue();
                parseValue(sb, value);

                if (entries.hasNext())
                    sb.append(",");
            }
            sb.insert(0, "{").append("}");
            return sb.toString();
        } else if (obj instanceof List) {
            List dataList = (List) obj;
            if (dataList == null)
                return "[]";

            StringBuffer sb = new StringBuffer();

            Iterator entries = dataList.iterator();
            while (entries.hasNext()) {
                Object value = entries.next();
                parseValue(sb, value);

                if (entries.hasNext())
                    sb.append(",");

            }
            sb.insert(0, "[").append("]");

            return sb.toString();
        } else
            throw new RuntimeException("类型不支持");
    }

    private void parseValue(StringBuffer sb, Object value) {
        if (value == null)
            sb.append("null");
        else if (value instanceof Integer || value instanceof Double || value instanceof Float)
            sb.append(value);
        else if (value instanceof Map || value instanceof List)
            sb.append(toJSONString(value));
        else
            sb.append("\"" + value + "\"");
    }
    //</editor-fold>


    //<editor-fold desc="Getter and Setter方法">
    public String getUrl() {
        return url;
    }

    public MyRequest setUrl(String url) {
        if (url == null || "".equals(url))
            throw new IllegalArgumentException("url不能为空或空串");

        this.url = url;
        return this;
    }

    public Map<String, Object> getQuery() {
        return query;
    }

    public MyRequest setQuery(Map<String, Object> query) {
        if (query == null)
            throw new IllegalArgumentException("param不能为空");

        this.query = query;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public MyRequest setMethod(String method) {
        if (method == null || "".equals(method))
            throw new IllegalArgumentException("method不能为空");

        this.method = method;
        return this;
    }

    public Map getPostData() {
        return postData;
    }

    public MyRequest setPostData(Map postData) {
        if (postData == null)
            throw new IllegalArgumentException("data不能为空");

        this.postData = postData;
        return this;
    }

    public String getCookies() {
        return cookies;
    }

    public MyRequest setCookies(String cookies) {
        if (cookies == null || "".equals(cookies))
            throw new IllegalArgumentException("cookie不能为空");

        this.cookies = cookies;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public MyRequest setHeaders(Map<String, String> headers) {
        if (headers == null)
            throw new IllegalArgumentException("headers不能为空");

        this.headers = headers;
        return this;
    }

    public String getEncode() {
        return encode;
    }

    public MyRequest setEncode(String encode) {
        if (encode == null || "".equals(encode))
            throw new IllegalArgumentException("encode不能为空");

        this.encode = encode;
        return this;
    }

    public boolean isDoInput() {
        return doInput;
    }

    public MyRequest setDoInput(boolean doInput) {
        this.doInput = doInput;
        return this;
    }

    public boolean isDoOutput() {
        return doOutput;
    }

    public MyRequest setDoOutput(boolean doOutput) {
        this.doOutput = doOutput;
        return this;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public MyRequest setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public MyRequest setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public boolean isUseCaches() {
        return useCaches;
    }

    public MyRequest setUseCaches(boolean useCaches) {
        this.useCaches = useCaches;
        return this;
    }

}
