package com.itheima.wechat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.util.SmsUtil;
import com.itheima.util.ValidateCodeUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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

    @RequestMapping("/findDetailById")
    public Result findDetailById(Integer id) {
        Setmeal setmeal = setmealService.findDetailById(id);
        return Result.success("", setmeal);
    }


}
