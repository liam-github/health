package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author liam
 * @description
 * @date 2020/2/23-16:56
 * @Version 1.0.0
 *
 * 加事务时，interfaceClass必须写，否则会导致发布的服务接口为SpringProxy，而不是CheckItemService接口
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //1.告诉mybatis我要分页
        Page<Object> page = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        //2.写一个普通的跟分页无关的查询
        List<CheckItem> checkItems = checkItemDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),checkItems);
    }
}
