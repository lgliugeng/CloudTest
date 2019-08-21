package com.liugeng.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liugeng.cloud.entity.AppletUserBak;

import java.util.List;

public interface AppletUserBakMapper extends BaseMapper<AppletUserBak> {

    void batchUpdateAppletUserBak(List<AppletUserBak> appletUserBakList);

    List<AppletUserBak> selectAppletUserBak();

}
