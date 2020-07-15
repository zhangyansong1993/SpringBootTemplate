package com.zys;
import com.zys.bean.UserBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {
    Logger logger = LoggerFactory.getLogger(ApplicationTest.class);

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate<Object,UserBean> myRedisTemplate;
    @Test
    public void test() {
        UserBean userBean=new UserBean(1,"27","188","男");
        myRedisTemplate.opsForValue().set("user",userBean);


    }


}
