package com.zys.controller;

import com.zys.bean.UserBean;
import com.zys.dao.testDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    @Autowired
    testDao td;

    @Cacheable(cacheNames = "user",cacheManager = "myRedisCacheManager")//指定反序列化的CacheManager
    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    public UserBean test(@PathVariable("id") Integer id) {
        System.out.println("查询");
        UserBean map = td.selectAll(id);

        return map;
    }
}
