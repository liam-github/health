package com.itheima.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author liam
 * @description
 * @date 2020/2/28-15:22
 * @Version 1.0.0
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
//        1.根据日期查询是否存在该日期的预约设置
        int count = orderSettingDao.findCountByDate(orderSetting.getOrderDate());
        System.out.println(orderSetting.getNumber());
        if (count > 0) {
//            2.存在，则只需要修改可预约数量
            orderSettingDao.editNumberByDate(orderSetting);
        } else {
//            3.不存在，则新增该记录
            orderSettingDao.add(orderSetting);
        }
    }

    @Override
    public void addBatch(ArrayList<OrderSetting> orderSettings) {
        for (OrderSetting orderSetting : orderSettings) {
            editNumberByDate(orderSetting);
        }
    }

    @Override
    public List<HashMap> findByMonth(String dateStr) {
//        1.查询当月的所有订单管理
        String startDate = dateStr + "-1";
        String endDate = dateStr + "-31";
        List<OrderSetting> orderSettings = orderSettingDao.findByMonth(startDate, endDate);

//        2.将其构造成前段需要的结果样式返回[{"date":22,"month":8,"number":300,"reservations":300},...]
        List<HashMap> maps = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(orderSettings)) {
            for (OrderSetting orderSetting : orderSettings) {

                Date orderDate = orderSetting.getOrderDate();
                int month = orderDate.getMonth();
                int date = orderDate.getDate();

                HashMap<String, Object> map = new HashMap<>();
                map.put("date", date);
                map.put("month", month);
                map.put("number", orderSetting.getNumber());
                map.put("reservations", orderSetting.getReservations());
                maps.add(map);
            }
        }
        return maps;
    }
}
