package com.zys.template;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;


//@Component
public class BaseElasticService {

    private Logger log = LoggerFactory.getLogger(BaseElasticService.class);
    @Autowired
    private RestHighLevelClient restHighLevelClient;


    /**
     * 查询ES
     *
     * @param idxName
     * @return
     */
    public List<JSONObject> search(String idxName, SearchSourceBuilder ssb) {
        List<JSONObject> res = new ArrayList<>();
        try {
            final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
            SearchRequest searchRequest = new SearchRequest(idxName);
            searchRequest.scroll(scroll);
            searchRequest.source(ssb);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            String scrollId = searchResponse.getScrollId();
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            while (searchHits != null && searchHits.length > 0) {
                for (SearchHit hit : searchHits) {
                    res.add(JSONObject.parseObject(hit.getSourceAsString()));
                }
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                scrollRequest.scroll(scroll);
                searchResponse = restHighLevelClient.scroll(scrollRequest, RequestOptions.DEFAULT);
                scrollId = searchResponse.getScrollId();
                searchHits = searchResponse.getHits().getHits();
            }
            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            clearScrollRequest.addScrollId(scrollId);
            ClearScrollResponse clearScrollResponse = restHighLevelClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
            boolean succeeded = clearScrollResponse.isSucceeded();
        } catch (Exception e) {
            log.error("查询ES出错" + e);
        }
        return res;
    }

    /**
     * 调用模板
     */
    public void test() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        boolQueryBuilder.must(termQuery("order_type.keyword", "故障工单"));
        boolQueryBuilder.must(termQuery("order_status", "2"));
        searchSourceBuilder.query(boolQueryBuilder);
        List<JSONObject> list = search("iams_ncolog_order*", searchSourceBuilder);

    }
}
