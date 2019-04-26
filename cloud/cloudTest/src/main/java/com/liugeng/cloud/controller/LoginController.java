package com.liugeng.cloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @Description:    登录页
* @Author:         liugeng
* @CreateDate:     2019/4/26 17:11
* @UpdateUser:     liugeng
* @UpdateDate:     2019/4/26 17:11
* @UpdateRemark:   修改内容
*/
@Controller
public class LoginController {

    /**
    * 方法说明   首页
    * @方法名    accessIndex
    * @参数      []
    * @返回值    java.lang.String
    * @异常
    * @创建时间  2019/4/26 17:11
    * @创建人    liugeng
    */
    @RequestMapping(value = "/index")
    public String accessIndex(){
        return "index";
    }
}
