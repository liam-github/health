package com.itheima.wechat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderInfo;
import com.itheima.service.OrderService;
import com.itheima.util.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        //1.校验验证码
        String telephone = orderInfo.getMember().getPhoneNumber();
        String validateCode = orderInfo.getValidateCode();

        String redisCode = jedisUtils.get(telephone + RedisMessageConstant.SENDTYPE_ORDER);

        //判断，如果redis中的验证码不存在或者，输入的验证码和redis中的验证码不相同，则返回错误信息
        if (redisCode == null || !redisCode.equals(validateCode)) {
            return Result.error(MessageConstant.VALIDATECODE_ERROR);
        }

        //设置订单类型为 微信订单
        orderInfo.setOrderType(Order.ORDERTYPE_WEIXIN);
        return orderService.submit(orderInfo);
    }
}
