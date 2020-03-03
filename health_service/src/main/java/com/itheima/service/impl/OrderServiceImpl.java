package com.itheima.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderInfo;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author liam
 * @description
 * @date 2020/3/2-19:41
 * @Version 1.0.0
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Override
    public Result submit(OrderInfo orderInfo) {

        Date orderDate = orderInfo.getOrderDate();
        Member member = orderInfo.getMember();
        int setmealId = orderInfo.getSetmealId();
        String orderType = orderInfo.getOrderType();

        //1.根据订单日期先查询当天是否存在预约设置
        OrderSetting orderSetting = orderSettingDao.findByDate(orderDate);
        if (orderSetting == null) {
            //如果没有预约设置，则返回提示信息：当天不能预约
            return Result.error(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        //2.判断当日是否已经约满
        int number = orderSetting.getNumber();
        int reservations = orderSetting.getReservations();
        if (reservations >= number) {
            //当已预约数大于等于可预约数时，返回提示信息：已经约满
            return Result.error(MessageConstant.ORDER_FULL);
        }

        //3.查询该手机号是否为公司会员,并获取会员id
        Member m = memberDao.findByTelephone(member.getPhoneNumber());
        if (m == null) {
            //不是则注册为会员
            member.setRegTime(new Date());
            memberDao.add(member);
            m = member;
        }
        Integer memberId = m.getId();

        //4.根据会员id，套餐id，预约时间 查询是否已经预约过
        Order order = new Order();
        order.setMemberId(memberId);
        order.setSetmealId(setmealId);
        order.setOrderDate(orderDate);
        order.setOrderType(orderType);
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        List<Order> orders = orderDao.findByCondition(order);
        if (CollectionUtil.isNotEmpty(orders)) {
            //存在预约，则返回提示消息：不能重复预约
            return Result.error(MessageConstant.HAS_ORDERED);
        }

        //5.不存在，则存储该预约信息，并更新预约设置表中的已经预约数
        orderDao.add(order);
        orderSettingDao.updateReservasionsById(orderSetting.getId());

        //6.返回提示信息：预约成功,并返回订单对象
        return Result.success(MessageConstant.ORDER_SUCCESS,order);
    }
}
