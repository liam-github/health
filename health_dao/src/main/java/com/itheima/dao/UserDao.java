package com.itheima.dao;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author liam
 * @description
 * @date 2020/3/4-22:12
 * @Version 1.0.0
 */
public interface UserDao {

    User findByUsername(@Param("username") String username);
}
