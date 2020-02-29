package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.exception.CheckGroupException;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author liam
 * @description 检查组控制器
 * @date 2020/2/24-19:24
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup) {
        checkGroupService.add(checkGroup);
        return Result.success(MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkGroupService.findPage(queryPageBean);
        return pageResult;
    }

    @RequestMapping("/findAll")
    public Result findAll() {
        List<CheckGroup> checkGroups = checkGroupService.findAll();
        return Result.success("", checkGroups);
    }

    @RequestMapping("/findById4Edit")
    public Result findById4Edit(Integer id) {
        Map map = checkGroupService.findById4Edit(id);
        return Result.success("", map);
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup) {
        checkGroupService.edit(checkGroup);
        return Result.success(MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("delete")
    public Result delete(Integer id) {
        try {
            checkGroupService.delete(id);
        } catch (CheckGroupException e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }

        return Result.success(MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
}
