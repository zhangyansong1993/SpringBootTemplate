package com.zys.config;


import com.zys.bean.UserBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.List;

@Configuration
public class MyRedisConfig {
    /**
     * 自定义RedisTemplate模板，把对象转json序列化到redis
     * 新的实体类需要再配置一份
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> myRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        template.setDefaultSerializer(serializer);
        return template;
    }

    /**
     * 自定义缓存管理器，使用redis缓存管理器
     * 新的实体类需要再配置一份
     * @param myRedisTemplate
     * @return 2.0springboot不支持
     */
//    @Bean
//    public RedisCacheManager myRedisCacheManager(RedisTemplate<Object, UserBean> myRedisTemplate) {
//        RedisCacheManager redisCacheManager = new RedisCacheManager(myRedisTemplate);
//        redisCacheManager.setUsePrefix(true);
//        return redisCacheManager;
//    }
//
//    @Primary //多个Template需要指定一个默认的
//    @Bean
//    public RedisCacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
//        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
//        cacheManager.setUsePrefix(true);
//        return cacheManager;
//    }


}


