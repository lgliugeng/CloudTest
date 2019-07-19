package com.liugeng.cloud.controller;

import com.liugeng.cloud.entity.ApiResult;
import com.liugeng.cloud.entity.User;
import com.liugeng.cloud.service.EsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/es")
@Api(value = "ES请求接口",tags = "ES请求接口")
public class EsController {

    @Autowired
    private EsService esService;

    @RequestMapping(value = "/addIndex/{index}",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "增加记录")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "索引",name = "index",required = true,dataType = "String"),
            @ApiImplicitParam(value = "用户信息",name = "user",required = true,dataType = "User")
    })
    public ApiResult addIndex(@PathVariable String index, @RequestBody User user){
        return esService.addIndex(index,user);
    }

    @RequestMapping(value = "/getIndex/{index}",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取记录")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "索引",name = "index",required = true,dataType = "String"),
            @ApiImplicitParam(value = "用户信息",name = "user",required = true,dataType = "User")
    })
    public ApiResult getIndex(@PathVariable String index, @RequestBody User user){
        return esService.getIndex(index,user);
    }

    @RequestMapping(value = "/updateIndex/{index}",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新记录")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "索引",name = "index",required = true,dataType = "String"),
            @ApiImplicitParam(value = "用户信息",name = "user",required = true,dataType = "User")
    })
    public ApiResult updateIndex(@PathVariable String index, @RequestBody User user){
        return esService.updateIndex(index,user);
    }

    @RequestMapping(value = "/deleteIndex/{index}",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除记录")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "索引",name = "index",required = true,dataType = "String"),
            @ApiImplicitParam(value = "用户信息",name = "user",required = true,dataType = "User")
    })
    public ApiResult deleteIndex(@PathVariable String index, @RequestBody User user){
        return esService.deleteIndex(index,user);
    }
}
