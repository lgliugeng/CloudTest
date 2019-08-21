package com.liugeng.cloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liugeng.cloud.entity.ApiResult;
import com.liugeng.cloud.entity.AppletUserBak;
import com.liugeng.cloud.mapper.AppletUserBakMapper;
import com.liugeng.cloud.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl extends ServiceImpl<AppletUserBakMapper,AppletUserBak> implements TestService {

    @Autowired
    private AppletUserBakMapper appletUserBakMapper;

    @Override
    public ApiResult batchUpdateAppletUserBak(){
        ApiResult apiResult = new ApiResult("200","ok");
        List<AppletUserBak> appletUserBakList = this.list();
        appletUserBakList.forEach(appletUserBak -> appletUserBak.setCountry("中国1"));
        appletUserBakMapper.batchUpdateAppletUserBak(appletUserBakList);
        return apiResult;
    }

    @Override
    public ApiResult selectAppletUserBak(){
        ApiResult apiResult = new ApiResult("200","ok");
        List<AppletUserBak> appletUserBak = appletUserBakMapper.selectAppletUserBak();
        apiResult.setData(appletUserBak);
        return apiResult;
    }
}
