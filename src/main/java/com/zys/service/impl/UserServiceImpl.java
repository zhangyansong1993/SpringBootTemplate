package com.zys.service.impl;

import com.zys.bean.UserBean;
import com.zys.dao.UserDao;
import com.zys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public UserBean selectAll(Integer id) {
        return userDao.selectAll(id);
    }
}
