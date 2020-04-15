package com.example.demo.system.utils;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HttpUtil {
    private static CloseableHttpClient httpClient;
    private static final String CHARSETUTF8 = "utf-8";
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    static {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(200);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(50);
        httpClient = HttpClients.custom().setConnectionTimeToLive(600, TimeUnit.SECONDS).setConnectionManager(poolingHttpClientConnectionManager).build();
    }

    /**
     * 处理get请求.
     *
     * @param url 请求路径
     * @return json
     * @throws Exception
     */
    public static String get(String url) throws Exception {
        logger.info("请求url：" + url);
        CloseableHttpResponse response = null;
        String result = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            response = httpClient.execute(httpGet);
            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line + System.getProperty("line.separator"));
            }
            in.close();
            result = sb.toString();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            logger.info("请求结果：" + result);
            if (null != response) {
                response.close();
            }
        }
        return result;
    }

    /**
     * 处理post请求.
     *
     * @param url    请求路径
     * @param params 参数
     * @return json
     * @throws IOException
     */
    public static String post(String url, Map<String, Object> params, Map<String, String> requestHeads) throws Exception {
        CloseableHttpResponse response = null;// 结果
        String result = null;
        try {
            HttpPost httpPost = new HttpPost(url);// 实例化post方法
            httpPost = setRequestHead(requestHeads, httpPost);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();// 处理参数
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
            }
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvps, CHARSETUTF8);// 提交的参数
            httpPost.setEntity(uefEntity);// 将参数给post方法
            response = httpClient.execute(httpPost);// 执行post方法
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), CHARSETUTF8);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            logger.info("请求结果：" + result);
            if (null != response) {
                response.close();
            }
        }
        return result;
    }

    /**
     * 处理post请求.
     *
     * @param url  请求路径
     * @param json 参数
     * @return json
     * @throws IOException
     */
    public static String postWithJSON(String url, String json, Map<String, String> requestHeads) throws Exception {
        logger.info("请求url：" + url);
        CloseableHttpResponse response = null;
        String result = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost = setRequestHead(requestHeads, httpPost);
            httpPost.setEntity(new StringEntity(json, CHARSETUTF8));
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), CHARSETUTF8);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            logger.info("请求结果：" + result);
            if (null != response) {
                response.close();
            }
        }
        return result;
    }

    /**
     * 处理post请求头
     *
     * @param requestHeads 参数
     * @return HttpPost
     */
    private static HttpPost setRequestHead(Map<String, String> requestHeads, HttpPost httpPost) {
//		httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");// application/x-www-form-urlencoded // multipart/form-data
        for (Map.Entry<String, String> entry : requestHeads.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }
        return httpPost;
    }

}
