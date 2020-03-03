package com.itheima.service;

import com.itheima.entity.Result;
import com.itheima.pojo.OrderInfo;

/**
 * @author liam
 * @description
 * @date 2020/3/2-19:40
 * @Version 1.0.0
 */
public interface OrderService {
    Result submit(OrderInfo orderInfo);
}
