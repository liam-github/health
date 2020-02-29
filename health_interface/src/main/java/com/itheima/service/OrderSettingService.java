package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.HashMap;
import java.util.List;

/**
 * @author liam
 * @description
 * @date 2020/2/28-15:21
 * @Version 1.0.0
 */
public interface OrderSettingService {

    void editNumberByDate(OrderSetting orderSetting);

    void addBatch(List<String[]> rows);

    List<HashMap> findByMonth(String dateStr);
}
