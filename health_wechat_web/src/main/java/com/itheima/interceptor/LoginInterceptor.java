package com.itheima.interceptor;

import com.itheima.entity.Result;
import com.itheima.util.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liam
 * @description 登录拦截器
 * @date 2020/3/3-19:31
 * @Version 1.0.0
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JedisUtils jedisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.取得参数中的token
        String token = request.getHeader("token");
//        此处token是否需要判空？
        if (token == null) {
            return false;
        }
        //2.根据token查询对应用户
        String memberStr = jedisUtils.get(token);

        return memberStr != null;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
