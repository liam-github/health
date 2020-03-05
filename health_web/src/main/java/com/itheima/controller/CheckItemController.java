package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.exception.CheckItemException;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liam
 * @description 检查项控制器
 * @date 2020/2/21-22:47
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/checkItem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * 1、form表单提交数据的格式 （name=jack&age=18&address=shangshou） 后端用对象接收不需要加注解
     * 2、页面传的是json数据，后端使用map 或者 pojo时 需要加@RequestBody
     * 3、基本类型 & 数组 & MultipartFile 只要保持页面的参数名称和controller方法形参一致就不用加@RequestParam
     * 4、List 不管名字一不一样 必须加@RequestParam
     */
    @RequestMapping("/add.do")
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/findPage.do")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkItemService.findPage(queryPageBean);
    }

    @RequestMapping("/findById")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public Result findById(Integer id) {
        CheckItem checkItem = checkItemService.findById(id);

        //不用判空吗,有可能已经被别人删除了 ?需要判断
        if(checkItem == null){
            //返回提示
        }
        return Result.success("", checkItem);
    }

    @RequestMapping("findAll")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public Result findAll() {
        List<CheckItem> checkItems = checkItemService.findAll();
        return Result.success("", checkItems);
    }

    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
    public Result edit(@RequestBody CheckItem checkItem) {

        checkItemService.edit(checkItem);
        return Result.success(MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('CHECKITEM_DELTE')")
    public Result delete(Integer id) {

        try {
            checkItemService.delete(id);
        } catch (CheckItemException e) {
            e.printStackTrace();
            //此异常说明有引用不能删除
            return Result.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
}
