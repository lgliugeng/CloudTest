package com.liugeng.cloud.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/redis")
@Api(value = "redis哨兵模式请求接口",tags = "redis哨兵模式请求接口")
public class RedisSentinelController {

    @Resource(name = "sentinelRedisTemplate")
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
