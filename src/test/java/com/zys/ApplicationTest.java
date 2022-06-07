package com.zys;


import com.zys.kafka.KafkaProducer;

import com.zys.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    UserService userService;

//    @Autowired
//    StringRedisTemplate redisTemplate;
//    @Autowired
//    KafkaProducer kafkaProducer;

//    @Qualifier("myRestTemplate")//如果容器中有多个类型相同的bean，使用此注解根据bean的名字指定注入
//    @Autowired
//    RestTemplate restTemplate;

//    @Test
//    public void test1() throws IOException {
//        kafkaProducer.send();
//    }

//    @Test
//    public void test2() throws IOException {
//        System.out.println(restTemplate.getForObject("http://www.baidu.com", String.class));
//    }
//    @Test
//    public void test3() {
//        ValueOperations<String, String> ops = redisTemplate.opsForValue();
////        ops.set("name","zys");
//        System.out.println(ops.get("name"));
//    }
    @Test
    public void test4(){
        System.out.println(userService.selectAll(1));

    }
}
