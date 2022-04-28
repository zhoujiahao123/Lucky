package com.uestc.luckyuser.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jacob
 * @date 2022/4/27 16:48
 */
@Configuration
public class ElasticSearchRestClientConfig {
    @Value("${elasticsearch.ip}")
    String ipAddressString;

    @Bean("highLevelClient")
    public RestHighLevelClient restHighLevelClient() {
        String[] addresses = ipAddressString.split(":");
        String ip = addresses[0];
        int port = Integer.parseInt(addresses[1]);
        HttpHost httpHost = new HttpHost(ip, port, "http");
        return new RestHighLevelClient(RestClient.builder(new HttpHost[]{httpHost}));
    }
}
