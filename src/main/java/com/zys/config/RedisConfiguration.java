//package com.zys.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.PropertySource;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * 单机版Jedis配置
// */
////@Configuration
//@PropertySource("classpath:redis/redis.properties")
//public class RedisConfiguration {
//
//    @Value("${redis.maxTotal}")
//    private Integer maxTotal;
//    @Value("${redis.host}")
//    private String host;
//    @Value("${redis.port}")
//    private Integer port;
//
//
//    public JedisPoolConfig jedisPoolConfig() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxTotal(maxTotal);
//        return jedisPoolConfig;
//    }
//
//    @Bean
//    public JedisPool jedisPool() {
//        JedisPoolConfig jedisPoolConfig = jedisPoolConfig();
//        return new JedisPool(jedisPoolConfig, host, port);
//    }
//}
