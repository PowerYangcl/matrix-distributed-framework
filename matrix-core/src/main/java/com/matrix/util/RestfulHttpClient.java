package com.matrix.util;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.*;

/**
 *@description: Restful风格调用工具类
 *   "http://blog.csdn.net/lnktoking/article/details/79174187"
 *@author Sjh
 *@date 2019/8/15 15:46
 *@version 1.0.1
 */


public class RestfulHttpClient {
    /** 全局默认请求头 */
    private static final Map<String, String> defaultHeaders = new HashMap<>();
    /** 全局连接初始化器 */
    private static List<URLConnectionInitializer> initializers;

    private static ExecutorService poolExecutor;
    private static final String charset = "UTF-8";
    private static final int readTimeout = 60000;
    private static final int connectTimeout = 60000;

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_PATCH = "PATCH";
    public static final String METHOD_DELETE = "DELETE";

    private RestfulHttpClient() {
    }

    /**
     * 发起请求
     *
     * @param method 请求方式：GET、POST、PUT、PATCH、DELETE
     * @param url    请求url
     * @param body   请求体body
     * @throws IOException
     */
    public static HttpResponse request(String method, String url, Object body) throws IOException {
        return request(method, url, body, defaultHeaders);
    }

    /**
     * 发起请求
     *
     * @param method  请求方式：GET、POST、PUT、PATCH、DELETE
     * @param url     请求url
     * @param body    请求体body
     * @param headers 请求头
     * @throws IOException
     */
    public static HttpResponse request(String method, String url, Object body, Map<String, String> headers) throws IOException {
        return getClient(url).method(method).body(body).headers(headers).request();
    }

    /**
     * 获取请求客户端
     *
     * @param url
     * @return
     */
    public static HttpClient getClient(String url) {
        return new HttpClient(url);
    }

    /**
     * 添加全局请求连接初始化器
     *
     * @param initializer
     */
    public static void addInitializer(URLConnectionInitializer initializer) {
        if (initializer == null) {
            throw new NullPointerException("不能添加空的连接初始化器");
        }
        if (initializers == null) {
            initializers = new ArrayList<>();
        }
        initializers.add(initializer);
    }

    /**
     *@description:将map转成参数类型
     *
     *@param
     *@author liwt
     *@date 2019/8/26 11:46
     *@return
     *@version 1.0.1
    */
    public static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        Iterator var2 = data.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry i = (Map.Entry)var2.next();

            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException var5) {
                var5.printStackTrace();
            }
        }

        return sb.toString();
    }

    /**
     * 请求客户端
     */
    public static class HttpClient {
        private Map<String, String> headers;
        private int readTimeout = RestfulHttpClient.readTimeout;
        private int connectTimeout = RestfulHttpClient.connectTimeout;
        private String method = METHOD_GET;
        private String url;
        private Map<String, String> pathParams;
        private Map<String, String> queryParams;
        private Map<String, String> postParams;
        private Object body;
        private HttpResponse response;
        private List<URLConnectionInitializer> initializers;

        public HttpClient(String url) {
            if (StringUtils.isBlank(url)) {
                throw new IllegalArgumentException("请求地址不能为空");
            }
            this.url = url;
            headers = new HashMap<>();
            pathParams = new HashMap<>();
            queryParams = new LinkedHashMap<>();
            postParams = new LinkedHashMap<>();
            headers.putAll(defaultHeaders);
            initializers = RestfulHttpClient.initializers;
        }

        public HttpClient get() {
            method = METHOD_GET;
            return this;
        }

        public HttpClient post() {
            method = METHOD_POST;
            return this;
        }

        public HttpClient put() {
            method = METHOD_PUT;
            return this;
        }

        public HttpClient patch() {
            method = METHOD_PATCH;
            return this;
        }

        public HttpClient delete() {
            method = METHOD_DELETE;
            return this;
        }

        /**
         * 添加请求头
         */
        public HttpClient addHeader(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        /**
         * 批量添加请求头
         */
        public HttpClient addHeaders(Map<String, String> headers) {
            this.headers.putAll(headers);
            return this;
        }

        /**
         * 设置读取超时时间
         */
        public HttpClient readTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        /**
         * 设置连接超时时间
         */
        public HttpClient connectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        /**
         * 重置请求头
         */
        public HttpClient headers(Map<String, String> headers) {
            this.headers.clear();
            this.headers.putAll(defaultHeaders);
            return addHeaders(headers);
        }

        /**
         * 设置请求方式，默认：GET
         */
        public HttpClient method(String method) {
            if (StringUtils.isBlank(method)) {
                throw new IllegalArgumentException("请求方式不能为空");
            }
            //请求方式不做限制
//            if(!ArrayUtils.contains(new String[]{METHOD_GET, METHOD_POST, METHOD_PUT, METHOD_PATCH, METHOD_DELETE}, method.toUpperCase())){
//                throw new IllegalArgumentException("请求方式设置错误，不能设置为：" + method);
//            }
            this.method = method.toUpperCase();
            return this;
        }

        /**
         * 设置请求地址，可带path参数
         * 如：/user/{id}
         */
        public HttpClient url(String url) {
            this.url = url;
            return this;
        }

        /**
         * 设置请求地址参数，替换url上的参数
         * 如：/user/{id} 上的{id}
         */
        public HttpClient pathParams(Map<String, String> pathParams) {
            this.pathParams.putAll(pathParams);
            return this;
        }

        /**
         * 添加请求地址参数，替换url上的参数
         * 如：/user/{id} 上的{id}
         */
        public HttpClient addPathParam(String key, String value) {
            this.pathParams.put(key, value);
            return this;
        }

        /**
         * 设置url请求参数，url问号后面的参数
         */
        public HttpClient queryParams(Map<String, String> queryParams) {
            this.queryParams.putAll(queryParams);
            return this;
        }

        /**
         * 添加url请求参数，url问号后面的参数
         */
        public HttpClient addQueryParam(String key, String value) {
            this.queryParams.put(key, value);
            return this;
        }

        /**
         * 设置表单参数，与body参数冲突，只能设置其中一个，优先使用body
         */
        public HttpClient postParams(Map<String, String> postParams) {
            this.postParams.putAll(postParams);
            return this;
        }

        /**
         * 添加表单参数
         */
        public HttpClient addPostParam(String key, String value) {
            this.postParams.put(key, value);
            return this;
        }

        /**
         * 设置请求体body，与post参数冲突，只能设置其中一个
         */
        public HttpClient bodyUrlencode(Object body) {
            this.body = urlencode(((Map<String,Object>)(body)));
            return this;
        }

        public HttpClient body(Object body) {
            this.body = body;
            return this;
        }

        /**
         * 获取最终的请求地址
         *
         * @return
         */
        public String getRequestUrl() {
            return transformUrl(this.url, pathParams, queryParams);
        }

        /**
         * 发起请求
         *
         * @return
         * @throws IOException
         */
        public HttpResponse request() throws IOException {
            response = RestfulHttpClient.request(this);
            return response;
        }

        /**
         * 发起异步请求
         *
         * @return
         */
        public Future<HttpResponse> asyncRequest() throws IOException {
            return getPoolExecutor().submit(new Callable<HttpResponse>() {
                @Override
                public HttpResponse call() throws Exception {
                    response = request();
                    return response;
                }
            });
        }

        /**
         * 添加请求连接初始化器
         *
         * @param initializer
         */
        public HttpClient addInitializer(URLConnectionInitializer initializer) {
            if (initializer == null) {
                throw new NullPointerException("不能添加空的连接初始化器");
            }
            if (initializers == null) {
                initializers = new ArrayList<>();
            }
            initializers.add(initializer);
            return this;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public int getReadTimeout() {
            return readTimeout;
        }

        public int getConnectTimeout() {
            return connectTimeout;
        }

        public String getMethod() {
            return method;
        }

        public String getUrl() {
            return url;
        }

        public Map<String, String> getQueryParams() {
            return queryParams;
        }

        public Map<String, String> getPathParams() {
            return pathParams;
        }

        public Map<String, String> getPostParams() {
            return postParams;
        }

        public <T> T getBody() {
            return (T) body;
        }

        public HttpResponse getResponse() {
            return response;
        }
    }

    /**
     * 请求响应结果
     */
    public static class HttpResponse {
        private int code;
        private Map<String, List<String>> headers;
        private String requestUrl;
        private String content;

        public HttpResponse(int code, Map<String, List<String>> headers, String requestUrl, String content) {
            this.code = code;
            this.headers = headers;
            this.requestUrl = requestUrl;
            this.content = content;
        }

        public <T> T getContent(Class<T> clz) throws IOException {
            if (StringUtils.isNotBlank(content)) {
                return new ObjectMapper().readValue(content, clz);
            }
            return null;
        }

        /**
         * 获取响应状态码
         */
        public int getCode() {
            return code;
        }

        /**
         * 获取响应头
         */
        public Map<String, List<String>> getHeaders() {
            return headers;
        }

        /**
         * 获取最后请求地址
         */
        public String getRequestUrl() {
            return requestUrl;
        }

        /**
         * 获取响应内容
         */
        public String getContent() {
            return content;
        }
    }

    /**
     * 发起请求
     *
     * @throws IOException
     */
    private static HttpResponse request(HttpClient client) throws IOException {
        HttpURLConnection con = instance(client);
        if (METHOD_GET.equalsIgnoreCase(client.getMethod())) {
            //GET请求，不用发请求体
            return readResponse(con);
        }
        String requestBody = null;
        if (isPrimitiveOrWrapClzOrStr(client.getBody())) {
            requestBody = client.getBody().toString();
        } else if (client.getBody() != null) {
            requestBody = new ObjectMapper().writeValueAsString(client.getBody());
            if (!client.getHeaders().containsKey("Content-Type")) {
                //设置请求媒体类型为json提交
                con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            }
        } else if (client.getPostParams() != null && !client.getPostParams().isEmpty()) {
            requestBody = toUrlParams(client.getPostParams());
        }

        con.setDoOutput(true);
        con.setDoInput(true);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), charset));
            if (requestBody != null) {
                //写入请求内容
                bw.write(requestBody);
            }
        } finally {
            if (bw != null) {
                bw.close();
            }
        }

        return readResponse(con);
    }

    /**
     * 将map参数转成url参数形式：name1=value2&name2=value2...
     *
     * @param paras
     * @return
     */
    public static String toUrlParams(Map<String, String> paras) {
        if (paras != null && !paras.isEmpty()) {
            StringBuilder urlParams = new StringBuilder();
            for (String k : paras.keySet()) {
                urlParams.append(k).append("=").append(paras.get(k)).append("&");
            }
            if (urlParams.length() > 0) {
                return urlParams.substring(0, urlParams.length() - 1);
            }
        }
        return null;
    }

    /**
     * 获取全局默认请求headers设置
     *
     * @return
     */
    public static Map<String, String> getDefaultHeaders() {
        return defaultHeaders;
    }

    /**
     * 设置默认全局默认请求headers
     *
     * @param headers
     * @return
     */
    public static Map<String, String> setDefaultHeaders(Map<String, String> headers) {
        defaultHeaders.clear();
        defaultHeaders.putAll(headers);
        return getDefaultHeaders();
    }

    /**
     * 设置默认全局默认请求headers
     *
     * @param poolExecutor 用来保存异常请求线程的线程池
     * @return
     */
    public static void setPoolExecutor(ExecutorService poolExecutor) {
        if (poolExecutor == null) {
            throw new NullPointerException("poolExecutor 不能为 null");
        }
        if (RestfulHttpClient.poolExecutor != null) {
            RestfulHttpClient.poolExecutor.shutdown();
        }
        RestfulHttpClient.poolExecutor = poolExecutor;
    }

    /**
     * 连接初始化器
     */
    public interface URLConnectionInitializer {
        /**
         * 初始化http请求连接对象
         *
         * @param connection
         * @return
         */
        HttpURLConnection init(HttpURLConnection connection, HttpClient client);
    }

    /**
     * 判断是否是字符串或基本类型或包装类型
     *
     * @param o
     * @return
     */
    private static boolean isPrimitiveOrWrapClzOrStr(Object o) {
        if (o == null) {
            return false;
        } else if (o instanceof String) {
            //是字符串类型
            return true;
        } else if (o.getClass().isPrimitive()) {
            //是基本类型
            return true;
        } else {
            try {
                //是包装类型
                return ((Class) o.getClass().getField("TYPE").get(null)).isPrimitive();
            } catch (Exception e) {
                return false;
            }
        }
    }

    /**
     * 读取响应结果
     *
     * @param con
     * @return
     * @throws IOException
     */
    private static HttpResponse readResponse(HttpURLConnection con) throws IOException {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
            StringBuilder sb = new StringBuilder();
            String read = null;
            while ((read = br.readLine()) != null) {
                sb.append(read);
                sb.append("\n");
            }
            br.close();
            return new HttpResponse(con.getResponseCode(), con.getHeaderFields(),
                    con.getURL().toString(), sb.toString());
        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            con.disconnect();
        }
        return null;
    }

    /**
     * 初始化请求连接
     *
     * @param client
     * @return
     * @throws IOException
     */
    private static HttpURLConnection instance(HttpClient client) throws IOException {
        URL u = new URL(client.getRequestUrl());
        HttpURLConnection con = (HttpURLConnection) u.openConnection();
        con.setReadTimeout(client.getReadTimeout());
        con.setConnectTimeout(client.getConnectTimeout());
        con.setRequestMethod(client.getMethod());
        Map<String, String> headers = client.getHeaders();
        if (headers != null && !headers.isEmpty()) {
            for (String key : headers.keySet()) {
                con.setRequestProperty(key, headers.get(key));
            }
        }

        List<URLConnectionInitializer> initializers = client.initializers;
        if (initializers != null && !initializers.isEmpty()) {
            for (URLConnectionInitializer initializer : initializers) {
                HttpURLConnection init = initializer.init(con, client);
                if (init != null) {
                    con = init;
                }
            }
        }
        return con;
    }

    /**
     * 处理url的参数
     * 如：/user/{id}，将id转成值
     *
     * @param url
     * @param pathParams  地址参数
     * @param queryParams 请求参数
     * @return
     */
    private static String transformUrl(String url, Map<String, String> pathParams, Map<String, String> queryParams) {
        if (pathParams != null && !pathParams.isEmpty()) {
            for (String key : pathParams.keySet()) {
                url = url.replaceAll("\\{" + key + "\\}", pathParams.get(key));
            }
        }
        if (queryParams != null && !queryParams.isEmpty()) {
            if (url.indexOf("?") > 0) {
                url += "&" + toUrlParams(queryParams);
            } else {
                url += "?" + toUrlParams(queryParams);
            }
        }
        return url;
    }

    private static ExecutorService getPoolExecutor() {
        if (poolExecutor == null) {
            poolExecutor = initPoolExecutor();
        }
        return poolExecutor;
    }

    private static ExecutorService initPoolExecutor() {
        int cpuCount = Runtime.getRuntime().availableProcessors();
        return new ThreadPoolExecutor(cpuCount, cpuCount + 10, 1, TimeUnit.MINUTES,
                new ArrayBlockingQueue<Runnable>(1024));
    }

//     try {
//        RestfulHttpClient.HttpResponse httpResponse = RestfulHttpClient.getClient(url)
//                .post()              //设置post请求
//                .addHeaders(headerMap) //设置url请求参数
//                .addPostParam("json", param) //设置入参
//                .request();//发起请求
//        if (httpResponse.getCode() == 200) {
//            //请求成功
//            System.out.println(httpResponse.getRequestUrl());  //响应内容
//        }
//    } catch (IOException e) {
//        e.printStackTrace();
//        System.err.println(e.getMessage());
//    }
}
