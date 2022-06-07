package com.zys.dao;

import com.zys.bean.UserBean;
import org.apache.ibatis.annotations.Mapper;

//@Mapper  在启动类上加上MapperScan注解就无需在dao层上加此注解了
public interface UserDao {
    UserBean selectAll(Integer id);
}
