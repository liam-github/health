package com.itheima.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.CheckGroupException;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckGroup checkGroup) {

        //此处要将checkItem和checkGroup的关系维护到 二者的关系表中，就需要先拿到checkGroup的主键
        //1.先插入checkGroup的基本信息并返回主键
        checkGroupDao.add(checkGroup);

        //2.获取主键
        Integer checkGroupId = checkGroup.getId();
        List<Integer> checkitemIds = checkGroup.getCheckitemIds();
        //3.插入关系表
        setCheckGroupAndCheckItem(checkGroupId, checkitemIds);

    }

    //编辑检查项和检查组关系的方法
    private void setCheckGroupAndCheckItem(Integer checkGroupId, List<Integer> checkitemIds) {
        //3.遍历取出checkitemId并插入 关系表中
        //for (Integer checkitemId : checkitemIds) {
        //    checkGroupDao.setCheckGroupAndCheckItem(checkGroupId,checkitemId);
        //}

        //如果数据库在北京，我在湖北，我要湖北北京往返1000次，这个效率就低了，我怎么解决了，我们采用批量提交参数
        //我们有很多方式批量提交：
        //1、mybatis可以开启批量提交,使用这个SqlSessionTemplate 把ExecutorType设置为BATCH,
        //    需要在mysql连接地址把批量提交参数加上，下去Baidu  https://www.cnblogs.com/123-shen/p/11226372.html


        if (CollectionUtil.isNotEmpty(checkitemIds)) {
            checkGroupDao.setCheckGroupAndCheckItemBatch(checkGroupId,checkitemIds);
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        Page<Object> page = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        List<CheckGroup> checkGroups = checkGroupDao.find(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),checkGroups);
    }

    @Override
    public Map findById4Edit(Integer id) {
        //1.查询这一条数据的基本信息
        CheckGroup checkGroup = checkGroupDao.findById(id);

        //2.查询所有的检查项
        List<CheckItem> checkItems = checkItemDao.findAll();

        //3.查询该检查项对应的检查组
        List<Integer> checkitemIds = checkGroupDao.findCheckItemIdsBycheckGroupId(id);

        Map<String, Object> map = new HashMap<>();
        map.put("checkGroup", checkGroup);
        map.put("checkItems", checkItems);
        map.put("checkitemIds", checkitemIds);
        return map;
    }

    @Override
    public void edit(CheckGroup checkGroup) {
        //1.先编辑检查组的基本信息
        checkGroupDao.edit(checkGroup);
        //2.删除原来的关系
        checkGroupDao.deleteAssociation(checkGroup.getId());
        //3.新建关系
        setCheckGroupAndCheckItem(checkGroup.getId(),checkGroup.getCheckitemIds());
    }

    @Override
    public void delete(Integer id) throws CheckGroupException{
        //1.查找是否存在引用该检查组的套餐，存在则不能删除
        Integer count = checkGroupDao.findCountSetmealByCheckGroupId(id);

        if (count > 0) {
            throw new CheckGroupException("存在包含该检查组的套餐，不能删除");
        } else {
            //删除对检查项的引用关系
            checkGroupDao.deleteAssociation(id);
            //删除基本信息
            checkGroupDao.delete(id);
        }
    }

    @Override
    public List<CheckGroup> findAll() {

        return checkGroupDao.findAll();
    }
}
