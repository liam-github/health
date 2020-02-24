package com.itheima.dao;

import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liam
 * @description
 * @date 2020/2/23-17:02
 * @Version 1.0.0
 */
public interface CheckItemDao {

    /**
     * 1、(单个参数)map & pojo 不需要加@Param
     * 2、多参数建议加@Param ,不加就需要按照param1 param2 ...paramN
     * 3、List & Array 可以不加@Param  如果不加@Param取值需要写list&array
     * 4、如果有多个List参数那么取值  param1 param2 ...paramN
     */
    void add(CheckItem checkItem);

    List<CheckItem> findPage(@Param("queryString") String queryString);
}
