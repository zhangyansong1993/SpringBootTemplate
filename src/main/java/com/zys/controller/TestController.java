package com.zys.controller;

import com.zys.bean.UserBean;
import com.zys.dao.testDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class TestController {

    @Autowired
    RestTemplate restTemplate;


//    @Autowired
//    testDao td;

    //@Async 异步
    //@Scheduled(cron = "0 * * * * MON-FRI") //定时任务,表达式匹配执行时间
//    @Cacheable(cacheNames = "user", cacheManager = "myRedisCacheManager")//指定反序列化的CacheManager
//    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
//    public UserBean test(@PathVariable("id") Integer id) {
//        System.out.println("查询");
//        UserBean map = td.selectAll(id);
//
//        return map;
//    }


   @GetMapping("/testa")
    public String test2() {
        String s=restTemplate.getForObject("http://PROVIDER-TICKET/test",String.class);
        return s;
    }
}
