package com.itheima.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liam
 * @description
 * @date 2020/2/27-18:25
 * @Version 1.0.0
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Override
    public void add(Setmeal setmeal) {
//        1.添加套餐基本信息
        setmealDao.add(setmeal);
//        2.添加该套餐与检查组的包含关系
        List<Integer> checkgroupIds = setmeal.getCheckgroupIds();
        setSetmealAndCheckgroupBatch(setmeal.getId(), checkgroupIds);
    }

    private void setSetmealAndCheckgroupBatch(Integer setmealId, List<Integer> checkgroupIds) {

        if (CollectionUtil.isNotEmpty(checkgroupIds)) {
            setmealDao.setSetmealAndCheckgroup(setmealId, checkgroupIds);
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        Page<Object> page = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        List<Setmeal> setmeals = setmealDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),setmeals);
    }
}
