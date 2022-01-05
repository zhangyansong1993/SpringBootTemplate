package com.zys;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zys.utils.ESUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    ESUtils esUtils;

    @Test
    public void test1() throws IOException {
        System.out.println(esUtils.sort("user"));
    }

    @Test
    public void test2() throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("name", "张三姐");
        obj.put("sex", "女");
        obj.put("age", 30);
        System.out.println(esUtils.update("user", "Abu9Cn4BtxoJVOTRqHQd", obj));
    }

    @Test
    public void test3() throws IOException {
        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("name", "张三");
        obj.put("sex", "女");
        obj.put("age", 30);
        JSONObject obj1 = new JSONObject();
        obj1.put("name", "张三");
        obj1.put("sex", "男");
        obj1.put("age", 300);
        array.add(obj);
        array.add(obj1);
        System.out.println(esUtils.list("user"));
    }
}
