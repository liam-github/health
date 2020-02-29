package com.itheima.dao;

import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liam
 * @description
 * @date 2020/2/27-18:28
 * @Version 1.0.0
 */
public interface SetmealDao {
    void add(Setmeal setmeal);

    void setSetmealAndCheckgroup(@Param("setmealId") Integer setmealId, @Param("checkgroupIds") List<Integer> checkgroupIds);

    List<Setmeal> findPage(String queryString);
}
