package com.zys.dao;

import com.zys.bean.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface testDao {
    //@Select("select * from testZys")
    UserBean selectAll(Integer id);
}
