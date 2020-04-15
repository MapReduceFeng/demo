package com.example.demo.system.elastic.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfigs {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchConfigs.class);

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        try {
            synchronized (ElasticSearchConfigs.class) {
                /** 用户认证对象 */
                final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                /** 设置账号密码 */
                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "123456"));
                /** 创建rest client对象 */
                RestClientBuilder builder = RestClient.builder(new HttpHost("192.168.1.129", 9600))
                        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                            @Override
                            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                            }
                        });
                return new RestHighLevelClient(builder);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
