package com.liugeng.cloud.controller;

import com.liugeng.cloud.common.util.ToolHttp;
import com.liugeng.cloud.entity.ApiResult;
import com.liugeng.cloud.service.DataToPdf;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cloud/test")
public class TestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataToPdf dataToPdf;


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

    public static void main(String[] args) {
        System.out.println();
    }
}
