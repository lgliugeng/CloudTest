package com.liugeng.cloud.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/redis/cluster")
@Api(value = "redis集群模式请求接口",tags = "redis集群模式请求接口")
public class RedisClusterController {

    @Resource(name = "clusterRedisTemplate")
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/getValueByKey/{key}",method = RequestMethod.GET)
    @ApiOperation(value = "获取值")
    @ApiImplicitParam(value = "key",name = "key",required = true,dataType = "String")
    public Object getValueByKey(@PathVariable("key") String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @RequestMapping(value = "/setValueByKey/{key}/{value}",method = RequestMethod.GET)
    @ApiOperation(value = "设置值")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "key",name = "key",required = true,dataType = "String"),
            @ApiImplicitParam(value = "value",name = "value",required = true,dataType = "String")
    })
    public Boolean setValueByKey(@PathVariable("key") String key,@PathVariable("value") String value) {
        redisTemplate.opsForValue().set(key,value);
        return true;
    }
}
