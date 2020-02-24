package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

/**
 * @author liam
 * @description 检查项服务接口
 * @date 2020/2/21-22:53
 * @Version 1.0.0
 */
public interface CheckItemService {

    void add(CheckItem checkItem);

    PageResult findPage(QueryPageBean queryPageBean);
}
