//package com.zys.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * 集群版Jedis配置  使用springboot-data-redis  不在使用此种方式
// */
////@Configuration
//@PropertySource("classpath:redis/redisCluster-${spring.profiles.active}.properties")
//public class RedisClusterConfiguration {
//
//    @Value("${redis.nodes}")
//    private String nodes;
//
//    @Bean
//    public JedisCluster jedisCluster() {
//        Set<HostAndPort> set = new HashSet<>();
//        String[] node = nodes.split(",");
//        for (String s : node) {
//            String[] nap = s.split(":");
//            set.add(new HostAndPort(nap[0],Integer.valueOf(nap[1])));
//        }
//        return new JedisCluster(set);
//    }
//}
