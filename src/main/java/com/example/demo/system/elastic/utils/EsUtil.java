package com.example.demo.system.elastic.utils;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EsUtil {
    public static RestHighLevelClient getClient() {
        /** 用户认证对象 */
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        /** 设置账号密码 */
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "VG4gTweoy2xxWhSr3T8b"));
        /** 创建rest client对象 */
        RestClientBuilder builder = RestClient.builder(new HttpHost("192.168.1.131", 9200))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }


    private static RestHighLevelClient client = null;

    public static RestHighLevelClient getClientStatic() {
        try {
            if (client != null) {
                return client;
            }
            synchronized (EsUtil.class) {
                if (client != null) {
                    return client;
                }
                long t = new Date().getTime();

                /** 用户认证对象 */
                final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                /** 设置账号密码 */
                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "VG4gTweoy2xxWhSr3T8b"));
                /** 创建rest client对象 */
                RestClientBuilder builder = RestClient.builder(new HttpHost("192.168.1.130", 9200),
                        new HttpHost("192.168.1.131", 9200),
                        new HttpHost("192.168.1.132", 9200))
                        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                            @Override
                            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                            }
                        });
                client = new RestHighLevelClient(builder);
                long t1 = new Date().getTime();
                System.out.println("获得连接，耗时:" + (t1 - t) + "s");
                return client;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 单条保存
     *
     * @param index
     * @param id
     * @param m
     */
    public static void saveData(String index, String id, Map<String, Object> m) {
        try {
//            RestHighLevelClient client = getClient();
            RestHighLevelClient client = EsUtil.getClientStatic();//130

            IndexRequest indexRequest = new IndexRequest(index)
                    .id(id)
                    .source(m);
            client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void getData(String index) {
        try {
            RestHighLevelClient client = getClient();//131
//            RestHighLevelClient client = EsUtil.getClientStatic();
            IndexRequest indexRequest = new IndexRequest(index);
            GetRequest getRequest = new GetRequest("global_house_list", index);
            GetResponse documentFields = client.get(getRequest, RequestOptions.DEFAULT);
            System.err.println(documentFields);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updata() {
        try {
            RestHighLevelClient client = EsUtil.getClientStatic();
            UpdateRequest updateRequest = new UpdateRequest("global_house_list", 1000 + "");
            Map<String, Object> source = new HashMap<String, Object>();
            source.put("content", "快递行业目前已经从高速增长1234");
            source.put("age", 999);
            updateRequest.doc(source);
            client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getSearch(String content) {
        try {
            RestHighLevelClient client = EsUtil.getClientStatic();
            SearchRequest searchRequest = new SearchRequest();
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.fetchSource("content", null);
            QueryBuilder queryBuilder = QueryBuilders.matchQuery("content", content);
            searchSourceBuilder.query(queryBuilder);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.err.println(searchResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Date d = new Date();
        String id = d.getTime() + "";
        id = "1000";
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("id", 1000);
        m.put("area_id", 1);
        m.put("camera_id", 1);
        m.put("content", "快递行业目前已经从高速增长期进入了成熟稳定增长期，增长速度从过去50%将逐步降低到20%左右");
        m.put("log_time", "2019-08-01 11:11:11");
        m.put("age", 1000);
//        EsUtil.saveData("global_house_list",id,m);
//        EsUtil.getData("1576842133289");
//        EsUtil.getData(id);
        EsUtil.getSearch("行业");
//        EsUtil.updata();
    }
}
