package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.CheckGroupException;
import com.itheima.pojo.CheckGroup;

import java.util.Map;

/**
 * @author liam
 * @description
 * @date 2020/2/24-19:31
 * @Version 1.0.0
 */
public interface CheckGroupService {

    void add(CheckGroup checkGroup);

    PageResult findPage(QueryPageBean queryPageBean);

    Map findById4Edit(Integer id);

    void edit(CheckGroup checkGroup);

    void delete(Integer id) throws CheckGroupException;
}
