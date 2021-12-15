package com.zys.dao;

import com.zys.bean.UserBean;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    UserBean selectAll(Integer id);
}
