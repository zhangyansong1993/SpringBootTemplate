package com.zys;

import com.alibaba.fastjson.JSONObject;
import com.zys.bean.UserBean;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private RedisTemplate myRedisTemplate;


    @Test
    public void test02() throws IOException {
        SearchRequest searchRequest = new SearchRequest("megacorp");
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit hit : searchHits) {
            System.out.println(hit.getSourceAsString());
        }

    }

    @Test
    public void test() {
        UserBean userBean=new UserBean(1,"张岩松","18","男");
        myRedisTemplate.opsForValue().set("user", userBean);
        String name=myRedisTemplate.opsForValue().get("user").toString();
        System.out.println(name);

    }

}
