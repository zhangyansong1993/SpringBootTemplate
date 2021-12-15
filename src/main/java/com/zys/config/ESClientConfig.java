//package com.zys.config;
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ESClientConfig{
//
//    @Bean
//    public RestHighLevelClient elasticsearchClient() {
//        RestClientBuilder builder = RestClient.builder(
//                new HttpHost("192.168.154.128", 9200),
//                new HttpHost("192.168.154.129",9200),
//                new HttpHost("192.168.154.130",9200));
//        return new RestHighLevelClient(builder);
//    }
//}
