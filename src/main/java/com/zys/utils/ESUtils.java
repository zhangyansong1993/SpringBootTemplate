package com.zys.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class ESUtils {

    @Autowired
    RestHighLevelClient client;

    private final RequestOptions options = RequestOptions.DEFAULT;

    /**
     * 判断索引是否存在
     */
    public boolean checkIndex(String index) {
        try {
            return client.indices().exists(new GetIndexRequest(index), options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * 创建索引
     */
    public boolean createIndex(String indexName, Map<String, Object> columnMap) {
        try {
            if (!checkIndex(indexName)) {
                CreateIndexRequest request = new CreateIndexRequest(indexName);
                if (columnMap != null && columnMap.size() > 0) {
                    Map<String, Object> source = new HashMap<>();
                    source.put("properties", columnMap);
                    request.mapping(source);
                }
                this.client.indices().create(request, options);
                return Boolean.TRUE;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * 删除索引
     */
    public boolean deleteIndex(String indexName) {
        try {
            if (checkIndex(indexName)) {
                DeleteIndexRequest request = new DeleteIndexRequest(indexName);
                AcknowledgedResponse response = client.indices().delete(request, options);
                return response.isAcknowledged();
                //return client.indices().delete(new DeleteIndexRequest(indexName), options).isAcknowledged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }


    /**
     * 写入数据
     */
    public boolean insert(String indexName, JSONObject obj) {
        try {
            IndexRequest indexRequest = new IndexRequest(indexName);
            indexRequest.source(obj, XContentType.JSON);
            IndexResponse index = this.client.index(indexRequest, options);
            System.out.println(index.getResult());
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * 批量写入数据
     */
    public boolean insertBatch(String indexName, JSONArray obj) {
        try {
            BulkRequest request = new BulkRequest(indexName);
            for (Object o : obj) {
                IndexRequest indexRequest = new IndexRequest();
                indexRequest.source(o.toString(), XContentType.JSON);
                request.add(indexRequest);
            }
            BulkResponse bulk = this.client.bulk(request, options);
            System.out.println(bulk.getTook());
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * 更新数据，可以直接修改索引结构
     */
    public boolean update(String indexName, String docId, JSONObject obj) {
        try {
            UpdateRequest updateRequest = new UpdateRequest(indexName, docId);
            //UpdateRequest updateRequest = new UpdateRequest();
            //or updateRequest.index("user").id("0001");
            updateRequest.doc(obj);
            this.client.update(updateRequest, options);
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * 条件更新
     */
    public boolean updateWhere(String indexName) {
        try {
            UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest(indexName);
//            updateByQueryRequest.indices("user");
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.must(QueryBuilders.matchPhraseQuery("name", "张三"));
            updateByQueryRequest.setQuery(builder);
            updateByQueryRequest.setScript(new Script("ctx._source['tel']=16666666666L")); //字符串单引号，数值加L
            //数据为存储而不是更新
            BulkByScrollResponse response = this.client.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
            System.out.println(response.getStatus().getUpdated());//更新数量
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * 删除数据
     */
    public boolean delete(String indexName, String id) {
        try {
            DeleteRequest deleteRequest = new DeleteRequest(indexName, id);
            this.client.delete(deleteRequest, options);
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * 批量删除数据
     */
    public boolean deleteBatch(String indexName, String[] array) {
        try {
            BulkRequest request = new BulkRequest(indexName);
            for (String str : array) {
                DeleteRequest deleteRequest = new DeleteRequest();
                deleteRequest.id(str);
                request.add(deleteRequest);
            }
            BulkResponse bulk = this.client.bulk(request, options);
            System.out.println(bulk.getTook());
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * 条件删除数据
     */
    public boolean deleteWhere(String indexName) {
        try {
            DeleteByQueryRequest delete = new DeleteByQueryRequest(indexName);
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.must(QueryBuilders.matchPhraseQuery("name", "张三"));
            delete.setQuery(builder);
            BulkByScrollResponse response = this.client.deleteByQuery(delete, options);
            System.out.println(response.getStatus().getDeleted());
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }


    /**
     * 查询
     */
    public List<Map> queryIndex(String indexName) {
        List<Map> list = new ArrayList();
        try {
            SearchRequest searchRequest = new SearchRequest(indexName);
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();//构建搜索条件
            queryBuilder.must(QueryBuilders.matchAllQuery());//match模糊匹配,match_phrase完全匹配,must--and,should--or,mustNot--!=

            sourceBuilder.query(queryBuilder);
            searchRequest.source(sourceBuilder);

            SearchResponse search = client.search(searchRequest, options);
            SearchHit[] hits = search.getHits().getHits();
            for (SearchHit hit : hits) {
                list.add(hit.getSourceAsMap());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 查询总数
     */
    public Long count(String indexName) {
        // 指定创建时间
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.termQuery("createTime", 1611378102795L));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder);

        CountRequest countRequest = new CountRequest(indexName);
        countRequest.source(sourceBuilder);
        try {
            CountResponse countResponse = client.count(countRequest, options);
            return countResponse.getCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 查询总数
     */
    public Long count2(String indexName) {
        Long count = 0L;
        try {
            // 指定创建时间
            SearchRequest request = new SearchRequest(indexName);
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            queryBuilder.must(QueryBuilders.termQuery("createTime", 1611378102795L));

            sourceBuilder.query(queryBuilder);
            request.source(sourceBuilder);

            SearchResponse search = client.search(request, options);
            count = search.getHits().getTotalHits().value;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 条件查询
     */
    public List<Map<String, Object>> list(String indexName) {
        // 查询条件,指定时间并过滤指定字段值 termQuery分词精确查询 matchPhraseQuery完全匹配不分词
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.matchAllQuery());
        //matchQuery分词查询，matchPhraseQuery精确查询不分词，keyword类型字段不会被分词，text类型字段会被分词,字段index设为false不能被索引
        queryBuilder.must(QueryBuilders.matchPhraseQuery("name", "张三"));
//        queryBuilder.mustNot(QueryBuilders.matchPhraseQuery("name", "王五"));//mustNot需要搭配matchQuery
//        queryBuilder.filter(QueryBuilders.rangeQuery("age").gt(30));//范围查询  gt大于gte大于等于lt小于lte小于等于
//        queryBuilder.filter(QueryBuilders.rangeQuery("create").gte(new Date().getTime()));//范围查询

        sourceBuilder.query(queryBuilder);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse searchResp = client.search(searchRequest, options);
            List<Map<String, Object>> data = new ArrayList<>();
            SearchHit[] searchHitArr = searchResp.getHits().getHits();
            for (SearchHit searchHit : searchHitArr) {
                Map<String, Object> temp = searchHit.getSourceAsMap();
                temp.put("id", searchHit.getId());
                data.add(temp);
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 分页查询
     */
    public List<Map<String, Object>> page(String indexName, Integer offset, Integer size) {
        // 查询条件,指定时间并过滤指定字段值
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(offset);
        sourceBuilder.size(size);
        sourceBuilder.sort("createTime", SortOrder.DESC);//排序
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse searchResp = client.search(searchRequest, options);
            List<Map<String, Object>> data = new ArrayList<>();
            long count = searchResp.getHits().getTotalHits().value;//查询命中总条数
            SearchHit[] searchHitArr = searchResp.getHits().getHits();
            for (SearchHit searchHit : searchHitArr) {
                Map<String, Object> temp = searchHit.getSourceAsMap();
                temp.put("id", searchHit.getId());
                temp.put("count", count);
                data.add(temp);
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 聚合查询
     */
    public List<Map<String, Object>> grouping(String indexName) {
        // 查询条件,指定时间并过滤指定字段值field根据字段分组，ageGroup分组名称，自定义
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        AggregationBuilder field = AggregationBuilders.terms("ageGroup").field("age");

        sourceBuilder.aggregation(field);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse searchResp = client.search(searchRequest, options);
            List<Map<String, Object>> data = new ArrayList<>();
            SearchHit[] searchHitArr = searchResp.getHits().getHits();
            System.out.println(searchResp);
            for (SearchHit searchHit : searchHitArr) {
                data.add(searchHit.getSourceAsMap());
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * max、min、avg查询
     */
    public List<Map<String, Object>> max(String indexName) {
        // 查询条件,指定时间并过滤指定字段值
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        AggregationBuilder field = AggregationBuilders.max("maxAge").field("age");

        sourceBuilder.aggregation(field);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse searchResp = client.search(searchRequest, options);
            List<Map<String, Object>> data = new ArrayList<>();
            SearchHit[] searchHitArr = searchResp.getHits().getHits();
            System.out.println(searchResp);
            for (SearchHit searchHit : searchHitArr) {
                data.add(searchHit.getSourceAsMap());
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 排序规则
     */
    public List<Map<String, Object>> sort(String indexName) {
        // 先升序时间，在倒序年龄
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.sort("createTime", SortOrder.ASC);
        sourceBuilder.sort("age", SortOrder.DESC);
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse searchResp = client.search(searchRequest, options);
            List<Map<String, Object>> data = new ArrayList<>();
            SearchHit[] searchHitArr = searchResp.getHits().getHits();
            for (SearchHit searchHit : searchHitArr) {
                Map<String, Object> temp = searchHit.getSourceAsMap();
                temp.put("id", searchHit.getId());
                data.add(temp);
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 自定义排序规则
     */
    public List<Map<String, Object>> defSort(String indexName) {
        // 指定置换顺序的规则
        // [age 12-->60]\[age 19-->10]\[age 13-->30]\[age 18-->40],age其他值忽略为1
        Script script = new Script("def _ageSort = doc['age'].value == 12?60:" +
                "(doc['age'].value == 19?10:" +
                "(doc['age'].value == 13?30:" +
                "(doc['age'].value == 18?40:1)));" + "_ageSort;");
        ScriptSortBuilder sortBuilder = SortBuilders.scriptSort(script, ScriptSortBuilder.ScriptSortType.NUMBER);
        sortBuilder.order(SortOrder.ASC);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.sort(sortBuilder);
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse searchResp = client.search(searchRequest, options);
            List<Map<String, Object>> data = new ArrayList<>();
            SearchHit[] searchHitArr = searchResp.getHits().getHits();
            for (SearchHit searchHit : searchHitArr) {
                Map<String, Object> temp = searchHit.getSourceAsMap();
                temp.put("id", searchHit.getId());
                data.add(temp);
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("zys".hashCode());
    }
}
