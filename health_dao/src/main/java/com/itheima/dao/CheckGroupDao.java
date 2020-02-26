package com.itheima.dao;

import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liam
 * @description
 * @date 2020/2/24-19:57
 * @Version 1.0.0
 */
public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(@Param("checkGroupId") Integer checkGroupId,@Param("checkitemId") Integer checkitemId);

    List<CheckGroup> find(String queryString);

    void setCheckGroupAndCheckItemBatch(@Param("checkGroupId") Integer checkGroupId,@Param("checkitemIds") List<Integer> checkitemIds);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsBycheckGroupId(@Param("id") Integer id);

    void edit(CheckGroup checkGroup);

    void deleteAssociation(@Param("id") Integer id);

    Integer findCountSetmealByCheckGroupId(Integer id);

    void delete(Integer id);
}
