package com.itheima.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public List<Setmeal> getSetmeal() {
        return setmealDao.getSetmeal();
    }

    @Override
    public Setmeal findById(Integer id) {
        //1.根据id查询套餐的基本信息
        Setmeal setmeal = setmealDao.findById(id);
        //2.查询该套餐包含的检查组信息
        if (setmeal != null) {
            List<CheckGroup> checkGroups = setmealDao.findCheckGroupBySetmealId(id);
            //3.查询每一个检查组包含的所有检查项
            if (CollectionUtil.isNotEmpty(checkGroups)) {
                for (CheckGroup checkGroup : checkGroups) {
                    List<CheckItem> checkItems = setmealDao.findCheckItemByCheckGroupId(checkGroup.getId());
                    checkGroup.setCheckItems(checkItems);
                }
            }
            setmeal.setCheckGroups(checkGroups);
        }
        return setmeal;
    }

    @Override
    public Setmeal findByIdBatch(Integer id) {
        //1.根据id查询套餐的基本信息
        Setmeal setmeal = setmealDao.findById(id);
        //2.查询该套餐包含的检查组信息
        if (setmeal != null) {
            List<CheckGroup> checkGroups = setmealDao.findCheckGroupBySetmealId(id);
            //3.查询所有检查组包含的所有检查项
            List<Integer> checkGroupIds = getCheckGroupIds(checkGroups);
            List<CheckItem> checkItems = setmealDao.findCheckItemByCheckGroupIds(checkGroupIds);

            //4.根据checkGroupId将checkItem分组
            /*
            HashMap<Integer, List<CheckItem>> map = new HashMap<>();
            for (CheckItem checkItem : checkItems) {
                Integer checkGroupId = checkItem.getCheckGroupId();
                List<CheckItem> childs = map.get(checkGroupId);
                if (childs == null) {
                    childs = new ArrayList<>();
                    map.put(checkGroupId,childs);
                }
                childs.add(checkItem);
            }*/
            Map<Integer, List<CheckItem>> map = checkItems.stream().collect(Collectors.groupingBy(CheckItem::getCheckGroupId));

            //5.将检查项list放到对应的检查组里
            for (CheckGroup checkGroup : checkGroups) {
                Integer checkGroupId = checkGroup.getId();
                checkGroup.setCheckItems(map.get(checkGroupId));
            }
            setmeal.setCheckGroups(checkGroups);
        }
        return setmeal;
    }

    /**
     * 获取传入的checkGroups，获取所有的checkGroup的id并封装成list集合返回
     * @author liam
     * @date 2020/3/1-18:33
     * @param checkGroups：
     * @return java.util.List<java.lang.Integer>
     */
    private List<Integer> getCheckGroupIds(List<CheckGroup> checkGroups) {
        List<Integer> checkGroupIds = new ArrayList<>();
        for (CheckGroup checkGroup : checkGroups) {
            checkGroupIds.add(checkGroup.getId());
        }
        return checkGroupIds;
    }
}
