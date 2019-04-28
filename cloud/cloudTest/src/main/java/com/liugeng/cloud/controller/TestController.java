package com.liugeng.cloud.controller;

import com.liugeng.cloud.common.redis.RedisService;
import com.liugeng.cloud.common.util.ToolHttp;
import com.liugeng.cloud.entity.ApiResult;
import com.liugeng.cloud.service.DataToPdf;
import com.liugeng.cloud.service.ThreadDemoService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cloud/test")
public class TestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataToPdf dataToPdf;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ThreadDemoService threadDemoService;


    @RequestMapping(value = "/dataToPdf2")
    @ResponseBody
    public ApiResult dataToPdfTest2() throws Exception{
        ApiResult apiResult = new ApiResult("0","ok");
        //String s = ToolHttp.post(false,"http://localhost:8081/cloudTest/cloud/test/dataToPdf");
        apiResult = this.dataToPdfTest();
        //apiResult.setData(s);
        return apiResult;
    }
    @RequestMapping(value = "/dataToPdf")
    @ResponseBody
    public ApiResult dataToPdfTest()throws Exception{
        ApiResult apiResult = new ApiResult("0","ok");
        //获取数据库所有表
        List<Map<String,Object>> tableListAll = dataToPdf.getAllTable();
        //将数据库信息写入数据pdf文档
        apiResult = dataToPdf.dataToPdf(tableListAll,"E:/数据库文档.pdf"); throw new Exception("异常");

       //return apiResult;
    }

    @RequestMapping(value = "/redisSet")
    @ResponseBody
    public ApiResult redisSet(){
        ApiResult apiResult = new ApiResult("0","ok");
        boolean resultA = redisService.set("A账号",1000);
        boolean resultB = redisService.set("B账号",2000);
        Map<String,String> resultMap = new HashMap<String,String>();
        resultMap.put("A账号","A账号存入1000元结果："+ resultA);
        resultMap.put("B账号","B账号存入1000元结果："+ resultB);
        apiResult.setData(resultMap);
        return apiResult;
    }

    @RequestMapping(value = "/redisGet")
    @ResponseBody
    public ApiResult redisGet(){
        ApiResult apiResult = new ApiResult("0","ok");
        Integer valueA = redisService.get("A账号");
        Integer valueB = redisService.get("B账号");
        Map<String,String> resultMap = new HashMap<String,String>();
        resultMap.put("A账号","余额：" + valueA);
        resultMap.put("B账号","余额：" + valueB);
        apiResult.setData(resultMap);
        return apiResult;
    }

    @RequestMapping(value = "/threadDemo")
    @ResponseBody
    public ApiResult threadDemo(){
        ApiResult apiResult = new ApiResult("0","ok");
        apiResult = threadDemoService.executorsPoolDemo();
        return apiResult;
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
