package com.itheima.wechat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.util.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

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

    @Autowired
    private JedisUtils jedisUtils;

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

    @RequestMapping("/findDetailById")
    public Result findDetailById(Integer id) {
        Setmeal setmeal = setmealService.findDetailById(id);
        return Result.success("", setmeal);
    }

    @RequestMapping("/getToken")
    public Result getToken() {

        String token = UUID.randomUUID().toString();
        jedisUtils.setex(token,60*60*2,token);

        return Result.success("", token);
    }

}
