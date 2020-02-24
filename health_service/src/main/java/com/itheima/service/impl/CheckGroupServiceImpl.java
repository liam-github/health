package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.CheckGroupDao;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liam
 * @description
 * @date 2020/2/24-19:32
 * @Version 1.0.0
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional()
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup) {

        //此处要将checkItem和checkGroup的关系维护到 二者的关系表中，就需要先拿到checkGroup的主键
        //1.先插入checkGroup的基本信息并返回主键
        checkGroupDao.add(checkGroup);

        //2.获取主键
        Integer checkGroupId = checkGroup.getId();
        List<Integer> checkitemIds = checkGroup.getCheckitemIds();

        //3.遍历取出checkitemId并插入 关系表中
        for (Integer checkitemId : checkitemIds) {
            checkGroupDao.setCheckGroupAndCheckItem(checkGroupId,checkitemId);
        }
    }
}
