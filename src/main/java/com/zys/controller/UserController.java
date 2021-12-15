package com.zys.controller;


import com.alibaba.fastjson.JSON;
import com.zys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/getUser")
    public String getUser(Integer id) {
        return JSON.toJSONString(userService.selectAll(id));
    }
}
