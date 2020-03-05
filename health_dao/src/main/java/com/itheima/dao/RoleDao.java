package com.itheima.dao;

import com.itheima.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author liam
 * @description
 * @date 2020/3/4-22:13
 * @Version 1.0.0
 */
public interface RoleDao {

    Set<Role> findByUserId(@Param("userId") Integer userId);
}
