package com.itheima.dao;

import com.itheima.pojo.HotSetmeal;
import com.itheima.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    public void add(Order order);
    public List<Order> findByCondition(Order order);
    public Map findById4Detail(Integer id);
    public Integer findOrderCountByDate(String date);
    public Integer findOrderCountAfterDate(String date);
    public Integer findVisitsCountByDate(String date);
    public Integer findVisitsCountAfterDate(String date);
    public List<HotSetmeal> findHotSetmeal();

    Integer findOrderCountBetweenDate(@Param("begin") String begin,@Param("end") String end);

    void updatePayNo(@Param("outTradeNo") String outTradeNo,@Param("orderId") Integer orderId);

    void updatePayStatus(String outTradeNo);
}
