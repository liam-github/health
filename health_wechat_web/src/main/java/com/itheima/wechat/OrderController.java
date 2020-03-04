package com.itheima.wechat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.exception.OrderException;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderInfo;
import com.itheima.service.OrderService;
import com.itheima.util.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author liam
 * @description 订单控制器
 * @date 2020/3/2-18:56
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisUtils jedisUtils;

    @RequestMapping("/submit")
    public Result submit(@RequestBody OrderInfo orderInfo) {
        String telephone = orderInfo.getMember().getPhoneNumber();
        String validateCode = orderInfo.getValidateCode();
        String token = orderInfo.getToken();

        //0.增加校验token的步骤
        if (token == null) {
            return Result.error("非法请求");
        }
        //1.校验验证码
        String redisCode = jedisUtils.get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        if (redisCode == null || !redisCode.equals(validateCode)) {
            //如果redis中的验证码不存在或者，输入的验证码和redis中的验证码不相同，则返回错误信息
            return Result.error(MessageConstant.VALIDATECODE_ERROR);
        }

        //1.5增加删除token步骤，利用redis单线程特性来阻止表单重复提交
        Long row = jedisUtils.del(token);
        if (row==0L) {
            //(redis删除方法，删除成功返回值大于0，反之等于0)
            return Result.error("非法请求");
        }

        //2.设置订单类型为 微信订单，并下单
        orderInfo.setOrderType(Order.ORDERTYPE_WEIXIN);
        try {
            return orderService.submit(orderInfo);
        } catch (OrderException e) {
            e.printStackTrace();
            //更新失败则重置token，并递归调用自己的方法
            jedisUtils.setex(token,60*60,token);
            return submit(orderInfo);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {
        Map map = orderService.findById(id);
        return Result.success("", map);

    }
}
