package com.liugeng.cloud.service;

import com.alibaba.fastjson.JSONObject;
import com.liugeng.cloud.entity.ApiResult;
import com.liugeng.cloud.entity.User;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class EsService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public ApiResult addIndex(String index,User user){
        ApiResult apiResult = new ApiResult("200","success");
        IndexRequest indexRequest = new IndexRequest(index);
        indexRequest.id(user.getId());
        String json = JSONObject.toJSONString(user);
        indexRequest.source(json,XContentType.JSON);
        try {
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            if(null != indexResponse){
                if(indexResponse.getResult() == DocWriteResponse.Result.CREATED){
                    //
                }
                apiResult.setData(indexResponse.status());
            }else{
                apiResult.setCode("500");
                apiResult.setMsg("fail");
                apiResult.setData("响应为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setCode("500");
            apiResult.setMsg("fail");
            apiResult.setData(e.getMessage());
        }
        return apiResult;
    }

    public ApiResult getIndex(String index,User user){
        ApiResult apiResult = new ApiResult("200","success");
        GetRequest getRequest = new GetRequest(index,user.getId());
        try {
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            if(null != getResponse){
                apiResult.setData(getResponse.getSource());
            }else{
                apiResult.setCode("500");
                apiResult.setMsg("fail");
                apiResult.setData("响应为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setCode("500");
            apiResult.setMsg("fail");
            apiResult.setData(e.getMessage());
        }
        return apiResult;
    }

    public ApiResult updateIndex(String index,User user){
        ApiResult apiResult = new ApiResult("200","success");
        UpdateRequest updateRequest = new UpdateRequest(index,user.getId());
        String json = JSONObject.toJSONString(user);
        updateRequest.doc(json,XContentType.JSON);
        try {
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            if(null != updateResponse){
                apiResult.setData(updateResponse.getGetResult());
            }else{
                apiResult.setCode("500");
                apiResult.setMsg("fail");
                apiResult.setData("响应为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setCode("500");
            apiResult.setMsg("fail");
            apiResult.setData(e.getMessage());
        }
        return apiResult;
    }

    public ApiResult deleteIndex(String index,User user){
        ApiResult apiResult = new ApiResult("200","success");
        DeleteRequest deleteRequest = new DeleteRequest(index,user.getId());
        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            if(null != deleteResponse){
                apiResult.setData(deleteResponse.status());
            }else{
                apiResult.setCode("500");
                apiResult.setMsg("fail");
                apiResult.setData("响应为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setCode("500");
            apiResult.setMsg("fail");
            apiResult.setData(e.getMessage());
        }
        return apiResult;
    }

    public ApiResult listIndex(String index,Integer pageNum,Integer pageSize,JSONObject jsonObject){
        ApiResult apiResult = new ApiResult("200","success");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(pageNum - 1);
        searchSourceBuilder.size(pageSize);
        searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("id","5"))
                .must(QueryBuilders.fuzzyQuery("name","马")));
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
            if(null != searchResponse){
                apiResult.setData(searchResponse);
            }else{
                apiResult.setCode("500");
                apiResult.setMsg("fail");
                apiResult.setData("响应为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setCode("500");
            apiResult.setMsg("fail");
            apiResult.setData(e.getMessage());
        }
        return apiResult;
    }
}
