package com.liugeng.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liugeng.cloud.entity.ApiResult;
import com.liugeng.cloud.entity.AppletUserBak;

public interface TestService extends IService<AppletUserBak> {

    ApiResult batchUpdateAppletUserBak();

    ApiResult selectAppletUserBak();
}
