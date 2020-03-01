package com.itheima.wechat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liam
 * @description 微信端（前台）套餐控制器
 * @date 2020/3/1-13:15
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @RequestMapping("/getSetmeal")
    public Result getSetmeal() {
        List<Setmeal> setmeals = setmealService.getSetmeal();
        return Result.success("", setmeals);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {
        Setmeal setmeal = setmealService.findByIdBatch(id);
        return Result.success("", setmeal);
    }
}
