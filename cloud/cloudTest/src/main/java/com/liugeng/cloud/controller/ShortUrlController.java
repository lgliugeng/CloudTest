package com.liugeng.cloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
* @Description:    短链请求控制器
* @Author:         liugeng
* @CreateDate:     2019/4/26 16:59
* @UpdateUser:     liugeng
* @UpdateDate:     2019/4/26 16:59
* @UpdateRemark:   修改内容
*/
@Controller
public class ShortUrlController {

    /**
    * 方法说明   短链重定向
    * @方法名    getRediect
    * @参数      [request, mav, shortUrl]
    * @返回值    org.springframework.web.servlet.ModelAndView
    * @异常      
    * @创建时间  2019/4/26 17:09
    * @创建人    liugeng
    */
    @RequestMapping(value = "/cloudS/{shortUrl}")
    public ModelAndView getRediect(HttpServletRequest request, ModelAndView mav, @PathVariable("shortUrl")String shortUrl){
        mav.setViewName("redirect:" + "http://localhost:8081/cloudTest/index");
        return mav;
    }
}
