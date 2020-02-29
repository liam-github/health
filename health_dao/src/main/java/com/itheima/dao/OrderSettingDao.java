package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.Date;
import java.util.List;

/**
 * @author liam
 * @description
 * @date 2020/2/28-15:31
 * @Version 1.0.0
 */
public interface OrderSettingDao {

    int findCountByDate(Date orderDate);

    void editNumberByDate(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> findByMonth(@Param("startDate") String startDate,@Param("endDate") String endDate);
}
