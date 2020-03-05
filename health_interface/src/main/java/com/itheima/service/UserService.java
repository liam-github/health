package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.pojo.User;

/**
 * @author liam
 * @description
 * @date 2020/3/4-21:53
 * @Version 1.0.0
 */
public interface UserService {

    User findByUsername(String username);
}
