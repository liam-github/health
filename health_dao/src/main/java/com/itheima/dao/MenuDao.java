package com.itheima.dao;

import com.itheima.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liam
 * @description
 * @date 2020/3/9-14:12
 * @Version 1.0.0
 */
public interface MenuDao {

    List<Menu> findLevel1();

    List<Menu> findChildren(@Param("parentId") Integer parentId);
}
