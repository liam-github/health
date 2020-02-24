package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.CheckItemException;
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

    @Override
    public void delete(Integer id) throws CheckItemException {
        //根据检查项id查询是否存在引用该检查项的检查组
        Integer count = checkItemDao.findCountGroupByItemId(id);

        //判断count是否大于0，大于0则不能删除
        if (count > 0) {
            throw new CheckItemException("存在引用该检查组的检查项，不能删除");
        } else {
            checkItemDao.delete(id);
        }
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
