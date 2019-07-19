package com.liugeng.cloud.service;

import com.alibaba.fastjson.JSONObject;
import com.liugeng.cloud.entity.ApiResult;
import com.liugeng.cloud.entity.User;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
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
}
