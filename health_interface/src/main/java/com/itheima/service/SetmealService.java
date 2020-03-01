package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;

import java.util.List;

/**
 * @author liam
 * @description
 * @date 2020/2/27-18:24
 * @Version 1.0.0
 */
public interface SetmealService {

    void add(Setmeal setmeal);

    PageResult findPage(QueryPageBean queryPageBean);

    List<Setmeal> getSetmeal();

    Setmeal findById(Integer id);

    Setmeal findByIdBatch(Integer id);
}
