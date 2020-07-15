package com.zys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface testDao {
    //@Select("select * from testZys")
    Map<String, String> selectAll();
}
