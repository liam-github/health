package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkItemService.findPage(queryPageBean);
    }
}
