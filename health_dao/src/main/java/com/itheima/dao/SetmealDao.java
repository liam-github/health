package com.itheima.dao;

import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    List<Setmeal> getSetmeal();

    Setmeal findById(@Param("id") Integer id);

    List<CheckGroup> findCheckGroupBySetmealId(@Param("id") Integer id);

    List<CheckItem> findCheckItemByCheckGroupId(@Param("checkGroupId") Integer checkGroupId);

    List<CheckItem> findCheckItemByCheckGroupIds(@Param("checkGroupIds")List<Integer> checkGroupIds);

    List<Map<String, Object>> getSetmealReport();
}
