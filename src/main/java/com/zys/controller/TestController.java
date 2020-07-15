package com.zys.controller;

import com.zys.dao.testDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    @Autowired
    testDao td;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public Map<String,String> test(){

        Map<String,String>map=td.selectAll();

        return map;
    }
}
