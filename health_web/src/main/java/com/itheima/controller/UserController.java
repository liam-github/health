package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liam
 * @description 用户控制器
 * @date 2020/3/5-16:31
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/getUsername")
    public Result getUsername() {
        //此处是从spring-security上下文中获取目前登录的user对象，注意：是spring的user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Result.success("", user.getUsername());
    }

}
