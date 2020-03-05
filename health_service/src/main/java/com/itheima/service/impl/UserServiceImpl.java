package com.itheima.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Set;

/**
 * @author liam
 * @description
 * @date 2020/3/4-21:55
 * @Version 1.0.0
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User findByUsername(String username) {
        //1.根据用户名查询用户
        User user = userDao.findByUsername(username);
        if (user == null) {
            //没有该用户名则直接返回
            return null;
        }

        //2.根据用户id查询出他所有的角色
        Integer userId = user.getId();
        Set<Role> roles = roleDao.findByUserId(userId);

        user.setRoles(roles);
        return user;
    }

}
