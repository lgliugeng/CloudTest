package com.liugeng.cloud.common.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.Instant;

/**
* @Description:    短链拦截器
* @Author:         liugeng
* @CreateDate:     2019/4/26 17:30
* @UpdateUser:     liugeng
* @UpdateDate:     2019/4/26 17:30
* @UpdateRemark:   修改内容
*/
public class ShortUrlInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String ip = request.getRemoteAddr();
        //http://localhost:8081/cloudTest/QzYr6r
        System.out.println("拦截器执行*****************************************************************************");
        String requestURI = request.getRequestURI();//获取请求url路径 例如：/cloudTest/aaaa
        String shortUrl = requestURI.replace("/cloudTest/","");//获取生成的短链值
        if(shortUrl.equals("zeaQrm")){//将短链进行比较，如果是短链则跳转到欢迎页
            response.sendRedirect("http://localhost:8081/cloudTest/index");
            return false;//进行重定向时需要返回false，如果为true则表示拦截的请求允许通过
        }else{//拦截所有方法跳转到错误页
            response.sendRedirect("http://localhost:8081/cloudTest/error.html");
            return false;//进行重定向时需要返回false，如果为true则表示拦截的请求允许通过
        }
    }

    // controller处理完成
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Instant startTime = (Instant) request.getAttribute("logrequestStartTime");

        Instant endTime = Instant.now();
        long executeTime = endTime.toEpochMilli()- startTime.toEpochMilli();

    }
}
