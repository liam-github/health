package com.itheima.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MenuDao;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liam
 * @description
 * @date 2020/3/9-14:06
 * @Version 1.0.0
 */
@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> findAll() {

        List<Menu> level1Menus = menuDao.findLevel1();
        if (CollectionUtil.isNotEmpty(level1Menus)) {
            findChildren(level1Menus);
        }
        return level1Menus;
    }

    private void findChildren(List<Menu> parents) {
        for (Menu parentMenu : parents) {
            List<Menu> children = menuDao.findChildren(parentMenu.getId());
            if (CollectionUtil.isNotEmpty(children)) {
                findChildren(children);
            }
            parentMenu.setChildren(children);
        }
    }
}
